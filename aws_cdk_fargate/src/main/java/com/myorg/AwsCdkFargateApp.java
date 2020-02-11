package com.myorg;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;

public class AwsCdkFargateApp {
    public static void main(final String[] args) {
        App app = new App();

        StackProps props = StackProps.builder()
                .env(Environment.builder()
                        .account("611825264259")
                        .region("eu-central-1")
                        .build())
                .build();

        new AwsCdkFargateStack(app, "AwsCdkTestStack", props);

        app.synth();
    }
}
