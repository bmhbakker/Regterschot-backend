package nl.aim.regterschotracing.domain.services;

import jakarta.inject.Inject;
import nl.aim.regterschotracing.datasource.dao.TabDAO;
import nl.aim.regterschotracing.presentation.dto.TabDTO;

import java.util.List;

/**
 * Controls all the data of the tabs.
 */
public class TabService {
    private TabDAO tabDAO;

    public void deleteTab(int tabId, String username) {
        tabDAO.deleteTab(tabId,username);
    }

    /**
     * Gets all tabs based on the user.
     *
     * @param user Should be a valid user.
     * @return user tabs based on the given user.
     */
    public List<TabDTO> getAll(String user) {
        return tabDAO.getUserTabs(user);
    }

    /**
     * Create a new tab based on the information that the user has given.
     * @param username defines which user makes the request
     * @param tabName defines which name the tab needs to have
     * @param raceID defines in which race the tab will be placed
     */
    public void createTab(String username, String tabName, int raceID) {
        tabDAO.createTab(username, tabName, raceID);
    }

    /**
     * Assigns tabDAO.
     * Sets a tab data access object.
     *
     * @param tabDAO Should be a TabDAO type.
     */
    @Inject
    public void setTabDAO(TabDAO tabDAO) {
        this.tabDAO = tabDAO;
    }


}
