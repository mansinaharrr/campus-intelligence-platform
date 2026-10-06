# API Plan

Base path: `/api/v1`

## Auth
- POST `/auth/register`
- POST `/auth/login`
- POST `/auth/refresh`
- POST `/auth/logout`
- GET `/auth/me`

## Locations
- GET `/locations`
- GET `/locations/{id}`

## Reports
- POST `/reports`
- GET `/reports`
- GET `/reports/{id}`
- DELETE `/reports/{id}`
- PUT `/reports/{id}/vote`
- DELETE `/reports/{id}/vote`

## Events
- GET `/events`
- GET `/events/active`
- GET `/events/{id}`

## Dashboard / Analytics
- GET `/dashboard/summary`
- GET `/analytics/overview`
- GET `/analytics/trends`
- GET `/analytics/peak-hours`
- GET `/analytics/categories`
- GET `/analytics/locations/ranking`
- GET `/analytics/events/metrics`

## Predictions
- GET `/predictions`
- GET `/predictions/peaks`

## Admin
- PATCH `/admin/events/{id}`
- GET/PATCH `/admin/reports`
- POST/PUT/DELETE `/admin/locations`
- GET/PATCH `/admin/users`
- GET `/admin/system/health`

## Internal Python API
Protected by service token:
- POST `/internal/v1/anomalies`
- POST `/internal/v1/predictions/batch`
- POST `/internal/v1/baselines/batch`
- POST `/internal/v1/model-runs`

WebSocket endpoint: `/ws`
- `/topic/events`
- `/topic/locations`
- `/topic/dashboard`
- `/user/queue/notifications`
