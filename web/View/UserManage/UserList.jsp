<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Management</title>
        <link rel="stylesheet" href="/Apotheke/View/UserManage/newcss.css">
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    </head>
    <body>
        <h1>User Management
        <div>
            <a href="${pageContext.request.contextPath}/logout" class ="dsdawe">
            <button>Logout</button>
            </a>
        </div>
            </h1>
        <div class="toolbar">
            <!-- Left section for Customer and Staff -->
            <div id="choserole">
                <c:choose>
                    <c:when test="${showcustomer}">                                      
                        Customer
                        <form name="show" method="post" action="CRUDUserList">
                            <input type="hidden" name="show" value="false"/>
                            <input type="submit" value="Staff"/>
                        </form>
                    </c:when>
                    <c:otherwise>           
                        <form name="show" method="post" action="CRUDUserList">
                            <input type="hidden" name="show" value="true"/>
                            <input type="submit" value="Customer"/>
                        </form>
                        Staff
                    </c:otherwise>
                </c:choose>
            </div>           

            <!-- Right section for search and filter -->
            <div id="searchfilter">
                <form name="search" method="post" action="CRUDUserList" style="display: inline-block;">
                    <input type="text" name="keyword" placeholder="Search..." value="${sessionScope.keyword}"/>
                    <input type="hidden" name="keywordReset" value="true"/>
                    <input type="submit" value="Search"/>
                </form>

                <!-- Filter button that opens the filter popup -->
                <button onclick="myFunction()" class="filter-btn">Filter</button>

                <!-- The actual filter popup -->
                <div class="popuptext" id="myPopup">
                    <c:choose>
                        <c:when test="${showcustomer}">                                      
                            <button class="close-btn" onclick="closePopup()">×</button>
                            <h3>Filter By</h3>
                            <form name="filter" method="post" action="CRUDUserList">
                                <label for="gender">Gender</label><br/>
                                <input type="checkbox" name="filter" value="male" ${fn:contains(sessionScope.filter, 'male') ? 'checked' : ''}> Male<br/>
                                <input type="checkbox" name="filter" value="female" ${fn:contains(sessionScope.filter, 'female') ? 'checked' : ''}> Female<br/><br/>

                                <label for="status">Status</label><br/>
                                <input type="checkbox" name="filter" value="active" ${fn:contains(sessionScope.filter, 'active') ? 'checked' : ''}> Active<br/>
                                <input type="checkbox" name="filter" value="inactive" ${fn:contains(sessionScope.filter, 'inactive') ? 'checked' : ''}> Inactive<br/><br/>

                                <input type="hidden" name="filterReset" value="true"/>

                                <button type="submit">Filter</button>
                                <button type="button" onclick="uncheckAll()">Clear</button>
                            </form>
                        </c:when>
                        <c:otherwise>           
                            <button class="close-btn" onclick="closePopup()">×</button>
                            <h3>Filter By</h3>
                            <form name="filter" method="post" action="CRUDUserList">
                                <label for="gender">Gender</label><br/>
                                <input type="checkbox" name="filter" value="male" ${fn:contains(sessionScope.filter, 'male') ? 'checked' : ''}> Male<br/>
                                <input type="checkbox" name="filter" value="female" ${fn:contains(sessionScope.filter, 'female') ? 'checked' : ''}> Female<br/><br/>


                                <label for="status">Status</label><br/>
                                <input type="checkbox" name="filter" value="active" ${fn:contains(sessionScope.filter, 'active') ? 'checked' : ''}> Active<br/>
                                <input type="checkbox" name="filter" value="inactive" ${fn:contains(sessionScope.filter, 'inactive') ? 'checked' : ''}> Inactive<br/><br/>

                                <label for="role">Role</label><br/>
                                <input type="checkbox" name="filter" value="warehouse" ${fn:contains(sessionScope.filter, 'warehouse') ? 'checked' : ''}> Warehouse<br/>
                                <input type="checkbox" name="filter" value="sale" ${fn:contains(sessionScope.filter, 'sale') ? 'checked' : ''}> Sale<br/>
                                <input type="checkbox" name="filter" value="marketing" ${fn:contains(sessionScope.filter, 'marketing') ? 'checked' : ''}> Marketing<br/><br/>

                                <input type="hidden" name="filterReset" value="true"/>

                                <button type="submit">Filter</button>
                                <button type="button" onclick="uncheckAll()">Clear</button>
                            </form>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>


        <div>
            <c:if test="${userList.isEmpty()}">
                <p class="message">There is no User!</p>
            </c:if>
        </div>

        <div>
            <c:choose>
                <c:when test="${showcustomer}">                                      
                    <c:if test="${not empty userList}">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Full Name</th>
                                    <th>Username</th>
                                    <th>Gender</th>
                                    <th>Phone</th>
                                    <th>Address</th>
                                    <th>Status</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${requestScope.userList}">
                                    <tr>
                                        <td><c:out value="${user.userID}"/></td>
                                        <td><c:out value="${user.fullname}"/></td>
                                        <td><c:out value="${user.username}"/></td>
                                        <td><c:out value="${user.gender}"/></td>
                                        <td><c:out value="${user.phone}"/></td>
                                        <td><c:out value="${user.address}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.status}">
                                                    Active
                                                </c:when>
                                                <c:otherwise>
                                                    Inactive
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.status}">
                                                    <form method="get" action="CRUDUserEditCustomer">
                                                        <input name="id" type="hidden" value="${user.userID}"/>
                                                        <input name="status" type="hidden" value="false"/>
                                                        <input type="submit" value="Deactive"/>
                                                    </form>                                                 
                                                </c:when>
                                                <c:otherwise>
                                                    <form method="get" action="CRUDUserEditCustomer">
                                                        <input name="id" type="hidden" value="${user.userID}"/>
                                                        <input name="status" type="hidden" value="true"/>
                                                        <input type="submit" value="Active"/>
                                                    </form>                                                  
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </c:when>
                <c:otherwise>           
                    <c:if test="${not empty userList}">

                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Full Name</th>
                                    <th>Username</th>
                                    <th>Gender</th>
                                    <th>Phone</th>
                                    <th>Role</th>
                                    <th>Address</th>
                                    <th>Status</th>
                                    <th><a href='CRUDUserAddStaff'"">Add Staff</a></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${requestScope.userList}">
                                    <tr>
                                        <td><c:out value="${user.userID}"/></td>
                                        <td><c:out value="${user.fullname}"/></td>
                                        <td><c:out value="${user.username}"/></td>
                                        <td><c:out value="${user.gender}"/></td>
                                        <td><c:out value="${user.phone}"/></td>
                                        <td><c:out value="${user.role.roleName}"/></td>
                                        <td><c:out value="${user.address}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.status}">
                                                    Active
                                                </c:when>
                                                <c:otherwise>
                                                    Inactive
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <form method="get" action="CRUDUserEditStaff">
                                                <input name="staffID" type="hidden" value="${user.userID}"/>
                                                <input type="submit" value="Edit"/>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="pagination" style="margin: auto; justify-content: center; margin-top: 30px;">
            <form action="CRUDUserList" method="post" style="display: inline;">
                <input type="hidden" name="indexCo" value="${indexNowCo - 1}" />
                <button type="submit">&laquo;</button>
            </form>

            <c:forEach begin="1" end="${endPageCo}" var="o">
                <form action="CRUDUserList" method="post" style="display: inline;">
                    <input type="hidden" name="indexCo" value="${o}" />
                    <button type="submit" class="${indexNowCo == o ? 'active' : ''}" ${indexNowCo == o ? 'disabled' : ''}>${o}</button>
                </form>
            </c:forEach>

            <form action="CRUDUserList" method="post" style="display: inline;">
                <input type="hidden" name="indexCo" value="${indexNowCo + 1}" />
                <button type="submit">&raquo;</button>
            </form>
        </div>




        
        <script>
            function setFormAction(url) {
                document.getElementById("UserForm").action = url;
            }

            function myFunction() {
                var popup = document.getElementById("myPopup");
                popup.classList.toggle("show");
            }

            function closePopup() {
                var popup = document.getElementById("myPopup");
                popup.classList.remove("show");
            }

            function uncheckAll() {
                document.querySelectorAll('input[type="checkbox"]')
                        .forEach(el => el.checked = false);
            }
        </script>
    </body>
</html>
