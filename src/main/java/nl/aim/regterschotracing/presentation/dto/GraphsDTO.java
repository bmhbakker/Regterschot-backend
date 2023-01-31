package nl.aim.regterschotracing.presentation.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object that carries all graph data between classes.
 */
public class GraphsDTO {
    String name;
    int tabId;
    int id;
    String type;
    List<Integer> gaugeSettings = new ArrayList<>();

    public GraphsDTO() {
        // Jakarta Injections needs this as default constructor.
    }


    /**
     * Gets the name of a graph.
     *
     * @return this graph name.
     */
    public String getName() {
        return name;
    }

    /**
     * Changes or sets the name of a graph.
     *
     * @param name Should be a graph name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the id of a tab.
     *
     * @return this tab id.
     */
    public int getTabId() {
        return tabId;
    }

    /**
     * Changes or sets the id of a tab.
     *
     * @param tabId Should be an id.
     */
    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    /**
     * Gets the id of a graph.
     *
     * @return this graph id.
     */
    public int getId() {
        return id;
    }

    /**
     * Changes or sets the id of a graph.
     *
     * @param id Should be an id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the type of graph.
     *
     * @return this graph type.
     */
    public String getType() {
        return type;
    }

    /**
     * Changes or sets the type of graph.
     *
     * @param type Should be a graph type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Adds the setting to the gaugeSettings
     *
     * @param setting are the settings to add.
     */
    public void addSetting(int setting) {
        this.gaugeSettings.add(setting);
    }

    /**
     * gets the graph settings
     * @return this graph settings
     */
    public List<Integer> getSettings() {
        return this.gaugeSettings;
    }

    /**
     * gets the gauge settings
     * @return this gaugeSettings
     */
    public List<Integer> getGaugeSettings() {
        return gaugeSettings;
    }

    /**
     * sets the gauge settings
     * @param gaugeSettings should be a list of settings for gauges
     */
    public void setGaugeSettings(List<Integer> gaugeSettings) {
        this.gaugeSettings = gaugeSettings;
    }
}
