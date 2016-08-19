<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select Timetable</title>
        <link rel="stylesheet" type="text/css" href="css/containerCss.css"/>
        <link rel="stylesheet" type="text/css" href="../css/containerCss.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.css"/>



        <script>
            function middleDiv() {
                var heightDiv = (document.documentElement.clientHeight) - 120 + "px";
                document.getElementById('middleDiv').style.height = heightDiv;
            }
            ;
        </script>
        <style>          
            .disDiv
            {
                background-color: rgba(180,180,180,0.7);
                height: ${sizeCol}px;
                width: 100%;
            }
        </style>
    </head>
    <body style="background-color: #F1F4F9" onload="middleDiv();">
        <div>
            <c:import url="header.jsp"/>
            <c:import url="navMenu.jsp"/>
        </div>
        <div id="middleDiv" class="container" style="background-color: white; overflow: auto;">
            <table style="border: 1px solid black; width: 97%; margin-top: 10px; margin-left: 20px">
                <tr style="border: 1px solid black; height: 25px;">
                    <th style="text-align: center">Monday</th>
                    <th style="text-align: center">Tuesday</th>
                    <th style="text-align: center">Wednesday</th>
                    <th style="text-align: center">Thursday</th>
                    <th style="text-align: center">Friday</th>
                    <th style="text-align: center">Saturday</th>
                    <th style="text-align: center">Sunday</th>
                </tr>
                <c:forEach var="MainList" items="${MainList}">
                    <tr style="width: 100%; border: 1px solid black; height: ${sizeCol}px">
                        <td style="border-right: 1px solid black; width: 14%">
                            <c:choose>
                                <c:when test="${Monday eq 'Monday'}">

                                    <c:forEach var="allocatedFacultyList" items="${allocatedFacultyList}">
                                        <c:if test="${MainList.mon eq allocatedFacultyList.faculty_allocation_subject_id}"> 
                                            <c:choose>
                                                <c:when test="${allocatedFacultyList.subject_name eq 'blank'}">

                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${allocatedFacultyList.subject_name eq 'break'}">
                                                            <div style="background-color: yellowgreen;height: ${sizeCol}px;">
                                                                <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>
                                                                <br>
                                                                <div style="font-weight: bolder;font-size: medium" align="center">BREAK</div>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div style="background-color: #05a2d0;height: ${sizeCol}px;color: white">
                                                                <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>

                                                                <div style="font-weight: bolder;font-size: small;margin-top: 10px" align="center">

                                                                    ${allocatedFacultyList.subject_name}<br>
                                                                    ${allocatedFacultyList.fName} ${allocatedFacultyList.lName}
                                                                </div>
                                                            </div>
                                                        </c:otherwise>    
                                                    </c:choose>
                                                </c:otherwise>    
                                            </c:choose>
                                        </c:if>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <div class="disDiv"></div> 
                                </c:otherwise>
                            </c:choose>                              
                        </td>
                        <td style="border-right: 1px solid black; width: 14%">
                            <c:choose>
                                <c:when test="${Tuesday eq 'Tuesday'}">
                                    <div>
                                        <c:forEach var="allocatedFacultyList" items="${allocatedFacultyList}">
                                            <c:if test="${MainList.tue eq allocatedFacultyList.faculty_allocation_subject_id}"> 
                                                <c:choose>
                                                    <c:when test="${allocatedFacultyList.subject_name eq 'blank'}">

                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${allocatedFacultyList.subject_name eq 'break'}">
                                                                <div style="background-color: yellowgreen;height: ${sizeCol}px;">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>
                                                                    <br>
                                                                    <div style="font-weight: bolder;font-size: medium" align="center">BREAK</div>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div style="background-color: #05a2d0;height: ${sizeCol}px;color: white">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>

                                                                    <div style="font-weight: bolder;font-size: small;margin-top: 10px" align="center">

                                                                        ${allocatedFacultyList.subject_name}<br>
                                                                        ${allocatedFacultyList.fName} ${allocatedFacultyList.lName}
                                                                    </div>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>    
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="disDiv" ></div>  
                                </c:otherwise>
                            </c:choose> 
                        </td>
                        <td style="border-right: 1px solid black; width: 14%">
                            <c:choose>
                                <c:when test="${Wednesday eq 'Wednesday'}">
                                    <div>
                                        <c:forEach var="allocatedFacultyList" items="${allocatedFacultyList}">
                                            <c:if test="${MainList.wed eq allocatedFacultyList.faculty_allocation_subject_id}"> 
                                                <c:choose>
                                                    <c:when test="${allocatedFacultyList.subject_name eq 'blank'}">

                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${allocatedFacultyList.subject_name eq 'break'}">
                                                                <div style="background-color: yellowgreen;height: ${sizeCol}px;">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>
                                                                    <br>
                                                                    <div style="font-weight: bolder;font-size: medium" align="center">BREAK</div>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div style="background-color: #05a2d0;height: ${sizeCol}px;color: white">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>

                                                                    <div style="font-weight: bolder;font-size: small;margin-top: 10px" align="center">

                                                                        ${allocatedFacultyList.subject_name}<br>
                                                                        ${allocatedFacultyList.fName} ${allocatedFacultyList.lName}
                                                                    </div>
                                                                </div>
                                                            </c:otherwise>   
                                                        </c:choose>
                                                    </c:otherwise>    
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="disDiv" ></div> 
                                </c:otherwise>
                            </c:choose> 
                        </td>
                        <td style="border-right: 1px solid black; width: 14%">
                            <c:choose>
                                <c:when test="${Thursday eq 'Thursday'}">
                                    <div>
                                        <c:forEach var="allocatedFacultyList" items="${allocatedFacultyList}">
                                            <c:if test="${MainList.thu eq allocatedFacultyList.faculty_allocation_subject_id}"> 
                                                <c:choose>
                                                    <c:when test="${allocatedFacultyList.subject_name eq 'blank'}">

                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${allocatedFacultyList.subject_name eq 'break'}">
                                                                <div style="background-color: yellowgreen;height: ${sizeCol}px;">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>
                                                                    <br>
                                                                    <div style="font-weight: bolder;font-size: medium" align="center">BREAK</div>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div style="background-color: #05a2d0;height: ${sizeCol}px;color: white">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>

                                                                    <div style="font-weight: bolder;font-size: small;margin-top: 10px" align="center">

                                                                        ${allocatedFacultyList.subject_name}<br>
                                                                        ${allocatedFacultyList.fName} ${allocatedFacultyList.lName}
                                                                    </div>
                                                                </div>
                                                            </c:otherwise>    
                                                        </c:choose>
                                                    </c:otherwise>    
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="disDiv"></div>
                                </c:otherwise>
                            </c:choose> 
                        </td>
                        <td style="border-right: 1px solid black; width: 14%">
                            <c:choose>
                                <c:when test="${Friday eq 'Friday'}">
                                    <div>
                                        <c:forEach var="allocatedFacultyList" items="${allocatedFacultyList}">
                                            <c:if test="${MainList.fri eq allocatedFacultyList.faculty_allocation_subject_id}"> 
                                                <c:choose>
                                                    <c:when test="${allocatedFacultyList.subject_name eq 'blank'}">

                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${allocatedFacultyList.subject_name eq 'break'}">
                                                                <div style="background-color: yellowgreen;height: ${sizeCol}px;">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>
                                                                    <br>
                                                                    <div style="font-weight: bolder;font-size: medium" align="center">BREAK</div>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div style="background-color: #05a2d0;height: ${sizeCol}px;color: white">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>

                                                                    <div style="font-weight: bolder;font-size: small;margin-top: 10px" align="center">

                                                                        ${allocatedFacultyList.subject_name}<br>
                                                                        ${allocatedFacultyList.fName} ${allocatedFacultyList.lName}
                                                                    </div>
                                                                </div>
                                                            </c:otherwise>    
                                                        </c:choose>
                                                    </c:otherwise>    
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="disDiv" ></div>  
                                </c:otherwise>
                            </c:choose> 
                        </td>
                        <td style="border-right: 1px solid black; width: 14%">
                            <c:choose>
                                <c:when test="${Saturday eq 'Saturday'}">
                                    <div>
                                        <c:forEach var="allocatedFacultyList" items="${allocatedFacultyList}">
                                            <c:if test="${MainList.sat eq allocatedFacultyList.faculty_allocation_subject_id}"> 
                                                <c:choose>
                                                    <c:when test="${allocatedFacultyList.subject_name eq 'blank'}">

                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${allocatedFacultyList.subject_name eq 'break'}">
                                                                <div style="background-color: yellowgreen;height: ${sizeCol}px;">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>
                                                                    <br>
                                                                    <div style="font-weight: bolder;font-size: medium" align="center">BREAK</div>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div style="background-color: #05a2d0;height: ${sizeCol}px;color: white">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>

                                                                    <div style="font-weight: bolder;font-size: small;margin-top: 10px" align="center">

                                                                        ${allocatedFacultyList.subject_name}<br>
                                                                        ${allocatedFacultyList.fName} ${allocatedFacultyList.lName}
                                                                    </div>
                                                                </div>
                                                            </c:otherwise>    
                                                        </c:choose>
                                                    </c:otherwise>    
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="disDiv" ></div>  
                                </c:otherwise>
                            </c:choose> 
                        </td>
                        <td style="width: 14%">
                            <c:choose>
                                <c:when test="${Sunday eq 'Sunday'}">
                                    <div>
                                        <c:forEach var="allocatedFacultyList" items="${allocatedFacultyList}">
                                            <c:if test="${MainList.sun eq allocatedFacultyList.faculty_allocation_subject_id}"> 
                                                <c:choose>
                                                    <c:when test="${allocatedFacultyList.subject_name eq 'blank'}">

                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${allocatedFacultyList.subject_name eq 'break'}">
                                                                <div style="background-color: yellowgreen;height: ${sizeCol}px;">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>
                                                                    <br>
                                                                    <div style="font-weight: bolder;font-size: medium" align="center">BREAK</div>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div style="background-color: #05a2d0;height: ${sizeCol}px;color: white">
                                                                    <div style="position: relative;top: 2px;left: 10px;font-weight: bolder;">${MainList.start} to ${MainList.end}</div>

                                                                    <div style="font-weight: bolder;font-size: small;margin-top: 10px" align="center">

                                                                        ${allocatedFacultyList.subject_name}<br>
                                                                        ${allocatedFacultyList.fName} ${allocatedFacultyList.lName}
                                                                    </div>
                                                                </div>
                                                            </c:otherwise>    
                                                        </c:choose>
                                                    </c:otherwise>    
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="disDiv" ></div> 
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div>
            <c:import url="footer.jsp"/>
        </div>
    </body>
</html>