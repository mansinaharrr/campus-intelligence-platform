# AWS Deployment Plan

Simple production story:
- EC2: Docker containers for Nginx, Spring Boot, and Python.
- RDS PostgreSQL: private database.
- S3: report images via presigned URLs.
- CloudWatch: logs and alarms.
- SSM Parameter Store: secrets.
- EC2 IAM role: AWS access without hard-coded keys.

Production network rules should restrict RDS to the EC2 security group.
