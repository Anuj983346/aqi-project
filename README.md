# AQI India - Simple Spring Boot Project
This is a minimal Spring Boot (Gradle) project that fetches Air Quality Index (AQI) data for Indian cities
from an external AQI provider. The project demonstrates a REST endpoint you can call locally to retrieve AQI.

## Features
- `/api/aqi?city={cityName}` endpoint that returns AQI data (proxied from configured external API).
- Configurable external API URL and API key via `application.properties` or environment variables.
- Simple service and controller structure for easy extension.

## Requirements
- Java 17+
- Gradle (or use your own Gradle wrapper)
- Network access to the external AQI API (set API key)

## How to run
1. Edit `src/main/resources/application.properties` and set `aqi.api.url` and `aqi.api.key`.
   Example (World Air Quality Index API):
   `aqi.api.url = https://api.waqi.info/feed/{city}/?token={API_KEY}`
2. Build and run:
   ```bash
   ./gradlew bootRun
   ```
3. Open in browser / Postman:
   `http://localhost:8080/api/aqi?city=Delhi`

## Notes
- This project does not ship with an API key. Obtain a key from your chosen AQI provider and configure it.
- The controller returns the upstream JSON as-is under `source` field and also extracts `aqi` if available.
