package com.vincentcodes.json;

public class ObjectMapperConfig {
    public static class Builder{
        private boolean debugModeOn = false;
        private boolean allowMissingProperty = false;
        private boolean processPropertyAnnotations = false;
        private boolean serializableAnnotationRequired = false;

        public Builder setDebugModeOn(boolean debugModeOn) {
            this.debugModeOn = debugModeOn;
            return this;
        }

        public Builder setAllowMissingProperty(boolean allowMissingProperty) {
            this.allowMissingProperty = allowMissingProperty;
            return this;
        }

        public Builder setProcessPropertyAnnotations(boolean processPropertyAnnotations) {
            this.processPropertyAnnotations = processPropertyAnnotations;
            return this;
        }

        public Builder setSerializableAnnotationRequired(boolean serializableAnnotationRequired) {
            this.serializableAnnotationRequired = serializableAnnotationRequired;
            return this;
        }

        public ObjectMapperConfig build(){
            ObjectMapperConfig config = new ObjectMapperConfig();
            config.debugModeOn = debugModeOn;
            config.allowMissingProperty = allowMissingProperty;
            config.processPropertyAnnotations = processPropertyAnnotations;
            config.serializableAnnotationRequired = serializableAnnotationRequired;
            return config;
        }
    }

    private ObjectMapperConfig(){}

    private boolean debugModeOn = false;
    private boolean allowMissingProperty = false;
    private boolean processPropertyAnnotations = false;
    private boolean serializableAnnotationRequired = false;

    public boolean isDebugModeOn() {
        return debugModeOn;
    }

    public boolean isAllowMissingProperty(){
        return allowMissingProperty;
    }

    public boolean isProcessPropertyAnnotations() {
        return processPropertyAnnotations;
    }

    public boolean isSerializableAnnotationRequired() {
        return serializableAnnotationRequired;
    }
}
