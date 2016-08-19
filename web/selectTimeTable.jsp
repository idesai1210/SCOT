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
    <body style="background-color: #F1F4F9" onload="middleDiv();">
        <div>
            <c:import url="header.jsp"/>
            <c:import url="navMenu.jsp"/>
        </div>
        <div id="middleDiv" class="container" style="background-color: white; overflow: auto;">
            <form name="timetable" class="form-horizontal" method="post" action="${pageContext.request.contextPath}/TimetableController/selectMainTimeTable">
               
                <input type="hidden" name="semester" value="${sem_id}" >
                <table align="center" class="x">
                    <tr style="height:20px;"></tr>
                    <tr>
                        <td class="labelDetail" style="text-align: right">
                            Select TimeTable
                        </td>
                        <td class="col-md-1"></td>
                        <td> 
                            <select name="timetable_id" id="timetable_id" class="form-control" style="margin-left: 5px; width: 250px">
                                <c:forEach var="divTimeTable" items="${divTimeTable}" varStatus="count">
                                <option value="${divTimeTable.timetable_track_id}">${count.count}</option>
                                </c:forEach>
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

