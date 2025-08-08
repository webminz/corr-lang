package io.corrlang.plugins.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonSchemaBean {

    @JsonProperty("$schema")
    private String schemaVersion;
    @JsonProperty("$id")
    private String id;

    private String title;

    private String description;

    private String type;

    private String format; // if string

    private Integer minLength; // if string

    private String pattern; // if string

    private Integer minimum; // if int

    private Integer maximum; // if int

    private List<String> required;

    @JsonProperty("$ref")
    private String typeReference;

    private Map<String, JsonSchemaBean> properties; // if object

    private JsonSchemaBean items; // if array

    public JsonSchemaBean(String schemaVersion, String id, String title, String description, String type, String format, Integer minLength, String pattern, Integer minimum, Integer maximum, List<String> required, String typeReference, Map<String, JsonSchemaBean> properties, JsonSchemaBean items) {
        this.schemaVersion = schemaVersion;
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.format = format;
        this.minLength = minLength;
        this.pattern = pattern;
        this.minimum = minimum;
        this.maximum = maximum;
        this.required = required;
        this.typeReference = typeReference;
        this.properties = properties;
        this.items = items;
    }

    public JsonSchemaBean() {
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public String getTypeReference() {
        return typeReference;
    }

    public void setTypeReference(String typeReference) {
        this.typeReference = typeReference;
    }

    public Map<String, JsonSchemaBean> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, JsonSchemaBean> properties) {
        this.properties = properties;
    }

    public JsonSchemaBean getItems() {
        return items;
    }

    public void setItems(JsonSchemaBean items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonSchemaBean that = (JsonSchemaBean) o;
        return Objects.equals(schemaVersion, that.schemaVersion) && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(type, that.type) && Objects.equals(format, that.format) && Objects.equals(minLength, that.minLength) && Objects.equals(pattern, that.pattern) && Objects.equals(minimum, that.minimum) && Objects.equals(maximum, that.maximum) && Objects.equals(required, that.required) && Objects.equals(typeReference, that.typeReference) && Objects.equals(properties, that.properties) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schemaVersion, id, title, description, type, format, minLength, pattern, minimum, maximum, required, typeReference, properties, items);
    }
}
