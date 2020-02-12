This project contains the infrastructure as code for an aws fargate example and a small demo application.

aws_cdk_fargate creates a Fargate cluster in AWS including VPC, public load balancer and auto scaling. It assumes that the image has been pushed to ecr and there is a role allowing containers to access dynamodb. The scaling policy is for demonstration purposes and not a production example.

greeting-provider is a small Java Spring Boot application with a Dockerfile.
