# Task

Create a PoC solution for a transport logistic company. The user should be able to CRUD Locations, Routes and view Route Plans, automatically generated by the solution.

Route Plan is an optimal way through all the cities specified in the Route. For simplicity each Location has `(x, y)` pair of coordinates, the Earth is flat, and we live in a Wonderland where each city is connected with all the others.

<a href="https://en.wikipedia.org/wiki/Travelling_salesman_problem" title="Such TSP much wow">
  <img src="http://i.imgur.com/lvMnIQX.jpg" alt="Such TSP much wow" width="240">
</a>

We expect a high load. So the performance of the Route Plan generation should not influence the performance of other calls to the API.

Solution should expose an API, which is described below.

# API

All request and response bodies should be a valid JSON.

## Errors

|Status code|Meaning|
|---|---|
|400|Bad syntax|
|404|Resource is not found|
|409|Related resource does not exist, or resource is used by some other resource|
|422|Validation failed|
|500|Something bad has happened and it's not client's fault 😞 |

## Locations

Warehouse locations, usually named after localities where they are positioned.

### Model

|Field|Type|Description|
|---|---|---|
|name|string|Name of the location|
|x|number|X coordinate|
|y|number|Y coordinate|
|head|boolean|If given Location can be a beginning of the Route|

### `POST /locations/`
<details>
  <summary>Example</summary><p>

Request:
```
POST /locations/

{ "name": "Morlaw", "x": 10.0500, "y": 33.1210 }
```

Response:
```
201 Created

{ "name": "Morlaw", "x": 10.0500, "y": 33.1210, "id": 1 }
```
</p></details>

### `GET /locations/:ID`
<details>
  <summary>Example</summary><p>

Request:
```
GET /locations/2
```

Response:
```
200 OK

{ "id": 2, "name": "Denver", "x": 39.76185, "y": -104.881105 }
```
</p></details>

### `PUT /locations/:ID`
<details>
  <summary>Example</summary><p>

Request:
```
PUT /locations/2

{ "name": "Not really Denver", "x": 39.76184, "y": -104.881106 }
```

Response:
```
204 No Content
```
</p></details>

### `DELETE /locations/:ID`
<details>
  <summary>Example</summary><p>

Request:
```
DELETE /locations/2
```

Response:
```
204 No Content
```
</p></details>

## Route 

Route is a list of Locations that a truck must visit. Relation between Route and Location is hasMany.

For each Route there is a Route Plan that is *generated in the background*, whenever Route or related Location changes.

Route head Location must have a `head` field set to `true`.

### Model

|Field|Type|Description|
|---|---|---|
|name|string|Name of the route|
|head|Location|Location where the route begins|
|locations|Location[]|Locations to visit|

### `POST /routes/`
<details>
  <summary>Example</summary><p>

Request:
```
POST /routes/

{ "name": "Interstate 60", "head": 1, "locations": [27, 33, 314, 466, 666, 1138] }
```

Response:
```
201 Created

{ "name": "Interstate 60", "head": 1, "locations": [27, 33, 314, 466, 666, 1138], "id": 1 }
```
</p></details>

### `GET /routes/:ID`
<details>
  <summary>Example</summary><p>

Request:
```
GET /routes/1
```

Response:
```
200 OK

{ "name": "Interstate 60", "head": 1, "locations": [27, 33, 314, 466, 666, 1138], "id": 1 }
```
</p></details>

### `PUT /routes/:ID`
<details>
  <summary>Example</summary><p>

Request:
```
PUT /routes/1

{ "head": 2 }
```

Response:
```
204 No Content
```
</p></details>

### `DELETE /routes/:ID`
<details>
  <summary>Example</summary><p>

Request:
```
DELETE /routes/1
```

Response:
```
204 No Content
```
</p></details>

## Route Plan

Route Plan is an optimal way through all the Locations defined in the Route. It should be generated in the background.

Route Plan should be (re)generated if any of the following events occurs:
- Route is created
- Route is updated (head, locations)
- Coordinates of the Route head or one of the locations are updated
- One of the locations is removed

### Model

|Field|Type|Description|
|---|---|---|
|plan|Location[]|Route Locations ordered according to the generated plan, starts with the Route head|

### `GET /routes/:ID/plan`
<details>
  <summary>Example (waiting for the plan to be generated)</summary><p>

Request:
```
GET /routes/1/plan
```

Response:
```
202 Accepted
Location: /routePlanQueue/100500
```
</p></details>

<details>
  <summary>Example (success)</summary><p>

Request:
```
GET /routes/1/plan
```

Response:
```
200 OK

{ "plan": [2, 666, 27, 1138, 314, 466, 666, 33] }
```
</p></details>

## Route Plan Queue

Resource designed for monitoring of the Route Plan generation status.

### Model

|Field|Type|Description|
|---|---|---|
|status|string|Status of the Route Plan generation. One of the following values `"pending"`, `"processing"`|

### `GET /routePlanQueue/:ID`
<details>
  <summary>Example (processing)</summary><p>

Request:
```
GET /routePlanQueue/100500
```

Response:
```
200 OK

{ "status": "processing" }
```
</p></details>

<details>
  <summary>Example (created)</summary><p>

Request:
```
GET /routePlanQueue/100500
```

Response:
```
303 See Other

Location: /routes/1/plan
```
</p></details>

# Implementation notes

- Use one of the modern Web frameworks (Spring Boot, Dropwizard, Play). In case of build tool, it's OK to go with Maven or SBT, since those are both alive and well-maintained.
- Keep in mind, that the size and the cost of everything matters.
- Please, be nice and implement TSP algorithm by yourself (all Google is yours, for sure).
- It's good to think about overloading, limits and possible attacks on the API.
