import {
  Box,
  Drawer,
  DrawerBody,
  DrawerCloseButton,
  DrawerContent,
  DrawerFooter,
  DrawerHeader,
  DrawerOverlay,
  FormLabel,
  Input,
  Stack,
  Button,
  Text,
  FormControl,
  InputGroup,
  InputRightElement,
  useToast,
} from "@chakra-ui/react";
import React from "react";
import { checkEmail, checkLogin, createUserAccount } from "../../Api";
import { UserContext } from "../../../App";
import ReCAPTCHA from "react-google-recaptcha";

const role = "User";
const loginErrorMessage = "Użytkownik o takiej nazwie już istnieje!";
const emailDuplicateErrorMessage =
  "Użytkownik o podanym adresie email już istnieje!";
const emailRegexErrorMessage = "Zły format adresu email!";
const passwordRegexErrorMessage =
  "Hasło musi zawierać przynajmniej 6 znaków, w tym cyfrę!";
const repeatPasswordErrorMessage = "Podane hasła muszą być identyczne!";
const regex = /^(?=.*[0-9])[a-zA-Z0-9]{6,16}$/;
const emailRegex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;

const checkPasswordRepeat = (password, repeatPassword) => {
  return password !== repeatPassword;
};

const checkPassword = (password) => {
  return !regex.test(password);
};

const checkEmailRegex = (email) => {
  return !emailRegex.test(email);
};

const SignUpDrawer = (props) => {
  const { setUser } = React.useContext(UserContext);
  const [showPassword, setShowPassword] = React.useState(false);
  const [showPasswordRepeat, setShowPasswordRepeat] = React.useState(false);

  const [login, setLogin] = React.useState("");
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [passwordRepeat, setPasswordRepeat] = React.useState("");

  const [loginError, setLoginError] = React.useState(false);
  const [emailRegexError, setEmailRegexError] = React.useState(false);
  const [emailDuplicateError, setEmailDuplicateError] = React.useState(false);
  const [passwordError, setPasswordError] = React.useState(false);
  const [passwordRepeatError, setPasswordRepeatError] = React.useState(false);

  const [recaptchaValue, setRecaptchaValue] = React.useState(false);

  const recaptchaRef = React.createRef();

  const handleClickP = () => setShowPassword(!showPassword);
  const handleClickPR = () => setShowPasswordRepeat(!showPasswordRepeat);
  const toast = useToast();

  const isLoginInDB = (login) => {
    checkLogin(login).then((response) => {
      setLoginError(response.data);
    });
  };
  const isEmailInDB = (email) => {
    checkEmail(email).then((response) => {
      setEmailDuplicateError(response.data);
    });
  };

  const clearState = () => {
    setLogin("");
    setLoginError(false);
    setEmail("");
    setEmailDuplicateError(false);
    setEmailRegexError(false);
    setPassword("");
    setPasswordError(false);
    setPasswordRepeat("");
    setPasswordRepeatError(false);
  };

  const createUser = () => {
    console.log("OKIEJ");
    createUserAccount({
      login: login,
      password: password,
      role: "User",
      email: email,
    })
      .then((response) => {
        setUser(response.data.result ?? null);
        if (response.data.result !== null) {
          localStorage.setItem(
            "nbaAppUser",
            JSON.stringify(response.data.result)
          );
        }
        toast({
          title: "Konto zostało stworzone!",
          description: "Zostałeś automatycznie zalogowany",
          status: "success",
          duration: 9000,
          isClosable: true,
        });
      })
      .catch(() => {
        toast({
          title: "Błąd po stronie serwera",
          description: "Spróbuj ponownie",
          status: "error",
          duration: 9000,
          isClosable: true,
        });
      });
  };

  const firstField = React.useRef();
  return (
    <Drawer
      isOpen={props.isOpen}
      placement="right"
      initialFocusRef={firstField}
      onClose={() => props.closeDrawer()}
    >
      <DrawerOverlay>
        <DrawerContent>
          <DrawerCloseButton
            onClick={() => {
              clearState();
              props.closeDrawer();
            }}
          />
          <DrawerHeader borderBottomWidth="1px">Utwórz konto</DrawerHeader>

          <DrawerBody>
            <Stack spacing="24px">
              <Box>
                <FormControl id="login" isRequired>
                  <FormLabel htmlFor="login">Użytkownik</FormLabel>
                  <Box>
                    <Input
                      ref={firstField}
                      id="username"
                      type="text"
                      placeholder="Wprowadź nazwę użytkownika"
                      isInvalid={loginError}
                      errorBorderColor="crimson"
                      value={login}
                      onChange={(event) => {
                        setLogin(event.target.value);
                        if (event.target.value.length > 0) {
                          isLoginInDB(event.target.value);
                        }
                      }}
                    />{" "}
                    {loginError ? (
                      <Text color="tomato">{loginErrorMessage}</Text>
                    ) : (
                      ""
                    )}
                  </Box>
                </FormControl>
              </Box>
              <Box>
                <FormControl id="email" isRequired>
                  <FormLabel htmlFor="email">Adres email</FormLabel>
                  <Box>
                    <Input
                      id="email"
                      placeholder="Podaj adres email"
                      isInvalid={emailRegexError || emailDuplicateError}
                      errorBorderColor="crimson"
                      value={email}
                      onChange={(event) => {
                        setEmail(event.target.value);
                        setEmailRegexError(checkEmailRegex(event.target.value));
                        if (event.target.value.length > 0) {
                          isEmailInDB(event.target.value);
                        }
                      }}
                    />{" "}
                    {emailRegexError ? (
                      <Text color="tomato">{emailRegexErrorMessage}</Text>
                    ) : (
                      ""
                    )}
                    {emailDuplicateError ? (
                      <Text color="tomato">{emailDuplicateErrorMessage}</Text>
                    ) : (
                      ""
                    )}
                  </Box>
                </FormControl>
              </Box>
              <Box>
                <FormControl id="password" isRequired>
                  <FormLabel>Hasło</FormLabel>
                  <InputGroup size="md">
                    <Box>
                      <Input
                        pr="4.5rem"
                        type={showPassword ? "text" : "password"}
                        placeholder="Wprowadź hasło"
                        isInvalid={passwordError}
                        errorBorderColor="crimson"
                        value={password}
                        onChange={(event) => {
                          setPassword(event.target.value);
                          setPasswordError(checkPassword(event.target.value));
                        }}
                      />
                      <InputRightElement width="4.5rem">
                        <Button h="1.75rem" size="sm" onClick={handleClickP}>
                          {showPassword ? "Ukryj" : "Pokaż"}
                        </Button>
                      </InputRightElement>{" "}
                      {passwordError ? (
                        <Text color="tomato">{passwordRegexErrorMessage}</Text>
                      ) : (
                        ""
                      )}
                    </Box>
                  </InputGroup>
                </FormControl>
              </Box>
              <Box>
                <FormControl id="passwordRepeat" isRequired>
                  <FormLabel>Potwierdź hasło</FormLabel>
                  <InputGroup size="md">
                    <Box>
                      <Input
                        pr="4.5rem"
                        type={showPasswordRepeat ? "text" : "password"}
                        placeholder="Potwierdź hasło"
                        isInvalid={passwordRepeatError}
                        errorBorderColor="crimson"
                        value={passwordRepeat}
                        onChange={(event) => {
                          setPasswordRepeat(event.target.value);
                          setPasswordRepeatError(
                            checkPasswordRepeat(password, event.target.value)
                          );
                        }}
                      />
                      <InputRightElement width="4.5rem">
                        <Button h="1.75rem" size="sm" onClick={handleClickPR}>
                          {showPasswordRepeat ? "Ukryj" : "Pokaż"}
                        </Button>
                      </InputRightElement>
                      {passwordRepeatError ? (
                        <Text color="tomato">{repeatPasswordErrorMessage}</Text>
                      ) : (
                        ""
                      )}
                    </Box>
                  </InputGroup>
                </FormControl>
              </Box>
              <Box>
                <ReCAPTCHA
                  sitekey="6LfRlSEaAAAAAMAXr_fepzMo7Xkow9f2bLTdlCtk"
                  size="compact"
                  ref={recaptchaRef}
                  onChange={() => setRecaptchaValue(true)}
                />
              </Box>
            </Stack>
          </DrawerBody>

          <DrawerFooter borderTopWidth="1px">
            <Button
              variant="outline"
              mr={3}
              onClick={() => {
                clearState();
                props.closeDrawer();
              }}
            >
              Anuluj
            </Button>
            <Button
              colorScheme="blue"
              onClick={() => {
                createUser();
                clearState();
                props.closeDrawer();
              }}
              disabled={
                emailRegexError ||
                emailDuplicateError ||
                loginError ||
                passwordError ||
                passwordRepeatError ||
                email === "" ||
                password === "" ||
                login === "" ||
                passwordRepeat === "" ||
                recaptchaValue === false
              }
            >
              Zatwierdź
            </Button>
          </DrawerFooter>
        </DrawerContent>
      </DrawerOverlay>
    </Drawer>
  );
};

export default SignUpDrawer;
