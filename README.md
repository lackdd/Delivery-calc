# API Documentation

## 1. `getDeliveryFee` (Controller)
**Location:** `DeliveryFeeController.java`  
**Purpose:** Handles HTTP GET requests to retrieve the delivery fee based on city and vehicle type.

### Parameters:
- `cityName` (String) – Name of the city (Tallinn, Tartu, Pärnu).
- `vehicle` (String) – Type of vehicle (Car, Scooter, Bike).

### Returns:
- `ResponseEntity<String>` – The calculated delivery fee or an error message.

### Exceptions:
- Returns a `400 Bad Request` if input is invalid or weather data is missing.

---

## 2. `getDeliveryFee` (Service)
**Location:** `DeliveryFeeService.java`  
**Purpose:** Computes the total delivery fee by considering the base fee and weather conditions.

### Parameters:
- `cityName` (String) – Name of the city.
- `vehicle` (String) – Type of vehicle.

### Returns:
- `String` – The final delivery fee in euros (e.g., `"3.5€"`).

### Logic:
1. Retrieves the latest weather data for the city.
2. Applies base fee based on the city and vehicle type.
3. Adjusts fee based on temperature, wind speed, and weather phenomena.

---

## 3. `fetchWeatherData`
**Location:** `WeatherService.java`  
**Purpose:** Fetches weather data from the Estonian Environment Agency API and saves it to the database.

### Parameters:
- None.

### Returns:
- `void`

### Logic:
1. Sends an HTTP request to retrieve XML weather data.
2. Filters data for Tallinn, Tartu, and Pärnu.
3. Maps the data into `Weather` entities and saves them in the database.

### Exceptions:
- Logs errors if API response is invalid.

---

## 4. `reportCurrentTime`
**Location:** `ScheduledTasks.java`  
**Purpose:** Periodically updates weather data every hour at HH:15.

### Parameters:
- None.

### Returns:
- `void`

### Logic:
- Calls `fetchWeatherData()` to refresh stored weather conditions.

---

## 5. `findByLatestDate`
**Location:** `WeatherRepository.java`  
**Purpose:** Fetches weather data from the database that is at most one hour old.

### Parameters:
- `oneHourAgo` (LocalDateTime) – The timestamp to filter recent weather data.

### Returns:
- `List<Weather>` – A list of weather records.

### Logic:
- Queries the database for weather updates recorded in the last hour.

---

This documentation provides a structured overview of the key methods used in the project. 🚀
