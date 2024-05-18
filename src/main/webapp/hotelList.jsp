<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hotel List</title>
</head>
<body>
<h1>Hotel List</h1>
<ul>
    <%-- Iterate over the hotels list and print out each hotel --%>
    <c:forEach var="hotel" items="${hotels}">
        <li>${hotel.name} - Latitude: ${hotel.latitude}, Longitude: ${hotel.longitude}</li>
    </c:forEach>
</ul>

<form action="hotelList" method="get">
    <label for="radius">Enter radius (in km):</label>
    <input type="number" id="radius" name="radius" required>
    <input type="hidden" name="action" value="filter">
    <input type="submit" value="Filter Hotels">
</form>

</body>
</html>
