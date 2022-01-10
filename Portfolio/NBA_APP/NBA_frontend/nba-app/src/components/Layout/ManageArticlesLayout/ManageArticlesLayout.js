import { Tab, TabList, TabPanel, TabPanels, Tabs } from "@chakra-ui/react";
import React from "react";
import EditDeleteArticle from "./EditDeleteArticle";
import NewArticleLayout from "./NewArticleLayout";

const ManageArticlesLayout = () => {
  const [changeCounter, setChangeCounter] = React.useState(0);
  return (
    <Tabs size="md" variant="enclosed">
      <TabList>
        <Tab>Nowy artykuł</Tab>
        <Tab>Zarządzaj artykułami</Tab>
      </TabList>
      <TabPanels>
        <TabPanel>
          <NewArticleLayout
            counter={changeCounter}
            changeCounter={() => setChangeCounter(changeCounter + 1)}
          />
        </TabPanel>
        <TabPanel>
          <EditDeleteArticle
            counter={changeCounter}
            changeCounter={() => setChangeCounter(changeCounter + 1)}
          />
        </TabPanel>
      </TabPanels>
    </Tabs>
  );
};

export default ManageArticlesLayout;
