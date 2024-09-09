import requests
import json

# Define the base URL of your API
BASE_URL = "http://localhost:8080"

# Define your test cases
test_cases = [
    {"title": "Test Case 1: Get all users", "method": "GET",
        "endpoint": "/users", "expected": [], "expected_status": 200},
    {"title": "Test Case 2: Create a new user", "method": "POST", "endpoint": "/users", "data": {"firstName": "test", "lastName": "test", "email": "testing@test.com"},
     "expected": {"id": 1, "firstName": "test", "lastName": "test", "email": "testing@test.com", "residenceId": None}, "expected_status": 201},
    {"title": "Test Case 3: Create another new user", "method": "POST", "endpoint": "/users", "data": {"firstName": "secondtehst", "lastName": "seconddtest", "email": "test@test.com"},
     "expected": {"id": 2, "firstName": "secondtehst", "lastName": "seconddtest", "email": "test@test.com", "residenceId": None}, "expected_status": 201},
]

# Function to make the request and compare the response


def test_endpoint(title, method, endpoint, data=None, expected=None, expected_status=200):
    url = BASE_URL + endpoint
    response = requests.request(method, url, json=data)

    # Check the status code
    assert response.status_code == expected_status, f"{
        title} - Expected {expected_status}, got {response.status_code}"

    # If we expect a response, check it
    if expected is not None:
        assert response.json() == expected, f"{
            title} - Expected {json.dumps(expected)}, got {json.dumps(response.json())}"

    print(f"\033[92m{title} - Passed\033[0m")


# Run the tests
for test in test_cases:
    test_endpoint(**test)
