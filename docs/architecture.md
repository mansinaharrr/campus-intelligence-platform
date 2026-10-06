# Architecture

The platform is a modular monolith: Spring Boot owns the transactional data and business rules; Python is separate only because it is a different runtime for analytics/ML.

```text
React + TypeScript
       |
 REST + JWT / STOMP WebSocket
       v
Spring Boot modular monolith
       |
       +---- PostgreSQL
       |
       +---- internal API <---- Python analytics service
       |
       +---- S3 (report images)
```

Core flow:
1. Student submits a report.
2. Spring validates and persists it.
3. After commit, the event detector clusters it with recent reports.
4. Event changes are broadcast through STOMP.
5. Python periodically reads historical rollups, computes baselines/anomalies/predictions, and posts results back through the internal API.
