import requests

# Define the base URL of your API
BASE_URL = "http://localhost:8080"

# Define your test cases
test_cases = [
    {"method": "GET", "endpoint": "/users", "expected": []},
    {"method": "POST", "endpoint": "/users", "data": {"firstName": "test", "lastName": "test", "email": "testing@test.com"},
        "expected": {"id": 1, "firstName": "test", "lastName": "test", "email": "testing@test.com"}},
    {"method": "GET", "endpoint": "/users/1",
        "expected": {"id": 1, "name": "Test User"}},
    {"method": "PUT", "endpoint": "/users/1", "data": {"name": "Updated User"},
        "expected": {"id": 1, "name": "Updated User"}},
    {"method": "DELETE", "endpoint": "/users/1",
        "expected": {"message": "User deleted"}},
]

# Function to make the request and compare the response


def test_endpoint(method, endpoint, data=None, expected=None):
    url = BASE_URL + endpoint
    response = requests.request(method, url, json=data)

    # Check the status code
    assert response.status_code == 200, f"Expected 200 OK, got {
        response.status_code}"

    # If we expect a response, check it
    if expected is not None:
        assert response.json() == expected, f"Expected {
            expected}, got {response.json()}"


# Run the tests
for test in test_cases:
    test_endpoint(**test)
