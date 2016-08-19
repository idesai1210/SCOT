<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Timetable</title>
        <link rel="stylesheet" type="text/css" href="css/containerCss.css"/>
        <link rel="stylesheet" type="text/css" href="../css/containerCss.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.css"/>
        <script type="text/javascript" src="/SCOT/dwr/engine.js" ></script>
        <script type="text/javascript" src="/SCOT/dwr/interface/timetableAjax.js"></script>
        <script type="text/javascript" src="/SCOT/dwr/util.js" ></script>


        <script>
            function middleDiv() {
                var heightDiv = (document.documentElement.clientHeight) - 120 + "px";
                document.getElementById('middleDiv').style.height = heightDiv;
            };

            var instituteList = function(data) {

                dwr.util.removeAllOptions('institute');
                dwr.util.addOptions('institute', data, 'institute_id', 'institute_name');
            };

            var departmentList = function(data) {

                dwr.util.removeAllOptions('department');
                dwr.util.addOptions('department', data, 'department_id', 'department_name');
            };

            var degreeList = function(data) {

                dwr.util.removeAllOptions('degree');
                dwr.util.addOptions('degree', data, 'degree_id', 'degree_name');
            };

            var semesterList = function(data) {

                dwr.util.removeAllOptions('semester');
                dwr.util.addOptions('semester', data, 'sem_id', 'sem_no');
            };

            var divisionList = function(data) {

                dwr.util.removeAllOptions('loadDivision');
                dwr.util.addOptions('loadDivision', data, 'div_id', 'div_name');
            };
        </script>
        <style>
            @font-face
            {
                font-family: Bebas Neue;
                src: url('../css/fonts/BebasNeue-webfont.ttf');
            }

            #headerDetail{
                white-space: nowrap;
                font-family: 'Bebas Neue';
                text-transform: uppercase;
                font-weight: normal;
                color: white;
                font-size: 40px;
                font-style: normal;
                text-shadow: 0 0 1px rgba(0,0,0,0.01);
                text-decoration: none;
            }

            .labelDetail{
                white-space: nowrap;
                font-family: 'Bebas Neue';
                text-transform: uppercase;
                font-weight: normal;
                color: white;
                font-size: 20px;
                color:black;
                padding-top: 6px;
            }
            .labelDetailGender{
                padding-top: 10px;
            }
        </style>
    </head>
    <body style="background-color: #F1F4F9" onload="middleDiv();
            timetableAjax.institute_detailShow(instituteList)">
        <div>
            <c:choose>
                <c:when test="${msg != null}">
                    <c:import url="header.jsp"/>
                </c:when>
                <c:otherwise>
                    <c:import url="header_1.jsp"/>
                </c:otherwise>
            </c:choose>
            <c:import url="navMenu.jsp"/>
        </div>
        <div id="middleDiv" class="container" style="background-color: white; overflow: auto;">
            <form name="timetable" class="form-horizontal" method="post" action="${pageContext.request.contextPath}/TimetableController/showTimeTable">
                <table align="center" class="x">
                    <tr style="height:20px;"></tr>
                    <tr>
                        <td class="labelDetail" style="text-align: right">
                            Select Institute
                        </td>
                        <td class="col-md-1"></td>
                        <td> 
                            <select name="institute" id="institute" class="form-control" style="margin-left: 5px; width: 250px" onchange="timetableAjax.select_department(this.value, departmentList);">
                                <option value="-1"></option>
                            </select> 
                        </td>
                    </tr>
                    <tr style="height:20px;"></tr>
                    <tr>
                        <td style="text-align: right">
                            <label class="labelDetail">
                                Select Department
                            </label>
                        </td>
                        <td class="col-md-1"></td>
                        <td style="padding-left: 5px"> 
                            <select name="department" id="department" class="form-control" style="width: 250px" onchange="timetableAjax.select_degree(this.value, degreeList);">
                                <option value="-1"></option>
                            </select> 
                        </td>
                    </tr>
                    <tr style="height:20px;"></tr>
                    <tr>
                        <td style="text-align: right">
                            <label class="labelDetail">
                                Select Degree
                            </label>
                        </td>
                        <td class="col-md-1"></td>
                        <td style="padding-left: 5px"> 
                            <select name="degree" id="degree" class="form-control" style="width: 250px" onchange="timetableAjax.select_semester(this.value, semesterList);">
                                <option value="-1"></option>
                            </select> 
                        </td>
                    </tr>
                    <tr style="height:20px;"></tr>
                    <tr>
                        <td style="text-align: right">
                            <label class="labelDetail">
                                Select Semester
                            </label>
                        </td>
                        <td class="col-md-1"></td>
                        <td style="padding-left: 5px"> 
                            <select name="semester" id="semester" class="form-control" style="width: 250px" onchange="timetableAjax.select_division(this.value, divisionList);">
                                <option value="-1"></option>
                            </select> 
                        </td>
                    </tr>
                    <tr style="height:20px;"></tr>
                    <tr>
                        <td style="text-align: right">
                            <label class="labelDetail">
                                Select Division
                            </label>
                        </td>
                        <td class="col-md-1"></td>
                        <td style="padding-left: 5px"> 
                            <select name="loadDivision" id="loadDivision" style="width: 250px" class="form-control">
                                <option value="-1"></option>
                            </select> 
                        </td>
                    </tr>
                    <tr style="height:20px;"></tr>
                    <tr>
                        <td align="right" colspan="3">
                            <input type="Submit" class="btn btn-primary form-control" value="Next" style="width: 150px">
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div>
            <c:import url="footer.jsp"/>
        </div>
    </body>
</html>
