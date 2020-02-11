package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.CpuUtilizationScalingProps;
import software.amazon.awscdk.services.ecs.ScalableTaskCount;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.iam.Role;


public class AwsCdkFargateStack extends Stack {
    public AwsCdkFargateStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public AwsCdkFargateStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);


        Vpc vpc = Vpc.Builder.create(this, "FargateVPC")
                .maxAzs(3)
                .build();

        Cluster cluster = Cluster.Builder.create(this, "FargateCluster")
                .vpc(vpc)
                .build();

        ApplicationLoadBalancedFargateService service = ApplicationLoadBalancedFargateService.Builder.create(this, "MyFargateService")
            .cluster(cluster)
            .cpu(1024)
            .memoryLimitMiB(2048)
            .desiredCount(2)
            .taskImageOptions(ApplicationLoadBalancedTaskImageOptions.builder()
                    .image(ContainerImage.fromEcrRepository(Repository.fromRepositoryName(this, "611825264259", "greetings")))
                    .taskRole(Role.fromRoleArn(this, "Role", "arn:aws:iam::611825264259:role/ContainerDynamoDBAccess"))
                    .containerPort(80)
                    .build())
            .publicLoadBalancer(true)
        .build();

        ScalableTaskCount scalableTaskCount = service.getService().autoScaleTaskCount(
                EnableScalingProps.builder()
                        .maxCapacity(4)
                        .minCapacity(2)
                        .build());

        scalableTaskCount.scaleOnCpuUtilization(
                "CpuUtilization",
                CpuUtilizationScalingProps.builder()
                        .targetUtilizationPercent(1)
                        .scaleOutCooldown(Duration.seconds(1))
                        .build());
    }
}
