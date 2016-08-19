package com.scot.controller;

import com.scot.bean.TableBean;
import com.scot.bean.UserBean;
import com.scot.dao.TableContentDao;
import com.scot.dao.createTimetableDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nisarg
 */
public class TimetableController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (request.getRequestURI().contains("data_required")) {

                List<TableBean> tableDropDownInstituteDetail = TableContentDao.institute_detailShow();
                List<TableBean> tableDropDownDepartmentDetail = TableContentDao.departmentDetailShow();
                List<TableBean> tableDropDownSemester = TableContentDao.semesterShow();
                List<TableBean> tableDropDownDivision = TableContentDao.divisionShow();
                List<TableBean> tableDropDownDegree = TableContentDao.degreeShow();

                request.setAttribute("tableDropDownInstituteDetail", tableDropDownInstituteDetail);
                request.setAttribute("tableDropDownDepartmentDetail", tableDropDownDepartmentDetail);
                request.setAttribute("tableDropDownSemester", tableDropDownSemester);
                request.setAttribute("tableDropDownDivision", tableDropDownDivision);
                request.setAttribute("tableDropDownDegree", tableDropDownDegree);
                RequestDispatcher rd = request.getRequestDispatcher("/timetable.jsp");
                rd.forward(request, response);
            } else if (request.getRequestURI().contains("createTimetable")) {

                HttpSession session = request.getSession();

                int institute_id = Integer.parseInt(request.getParameter("institute"));
                int department_id = Integer.parseInt(request.getParameter("department"));
                int sem_id = Integer.parseInt(request.getParameter("semester"));
                int div_id = Integer.parseInt(request.getParameter("loadDivision"));
                int start_time = Integer.parseInt(request.getParameter("start_time"));
                int total_duration = Integer.parseInt(request.getParameter("total_duration"));
                int total_break_day = Integer.parseInt(request.getParameter("total_break"));
                int totalClass[] = new int[total_duration];
                int track = 0;
                int forwardStartTime = start_time;
                for (int i = 0; i < total_duration; i++) {
                    if (start_time <= 12) {
                        totalClass[track] = start_time;
                        start_time++;
                    } else {
                        start_time = 1;
                        totalClass[track] = start_time;
                        start_time++;
                    }
                    track++;
                }

                int sizeCol = 560 / track;

                String days[] = request.getParameterValues("days");
                int dayTrack = days.length;

                String Monday = null, Tuesday = null, Wednesday = null, Thursday = null, Friday = null, Saturday = null, Sunday = null;

                for (String day : days) {
                    switch (day) {
                        case "Monday":
                            Monday = "Monday";
                            break;
                        case "Tuesday":
                            Tuesday = "Tuesday";
                            break;
                        case "Wednesday":
                            Wednesday = "Wednesday";
                            break;
                        case "Thursday":
                            Thursday = "Thursday";
                            break;
                        case "Friday":
                            Friday = "Friday";
                            break;
                        case "Saturday":
                            Saturday = "Saturday";
                            break;
                        case "Sunday":
                            Sunday = "Sunday";
                            break;
                    }
                }

//                String daysString = "";
//                for (String string : days) {
//
//                    daysString = daysString + "" + string + " ";
//                }
                TableBean bean = new TableBean();
                bean.setInstitute_id(institute_id);
                bean.setDepartment_id(department_id);
                bean.setSem_id(sem_id);
                bean.setDiv_id(div_id);

                List<TableBean> subjectList = createTimetableDao.getSubjectList(bean);
                List<TableBean> allocatedFacultyList = createTimetableDao.getFacultyList(subjectList);
                int dayTrackValue = total_break_day * dayTrack;

                TableBean beanBreak = new TableBean();
                beanBreak.setfName("BREAK");
                beanBreak.setRegistration_id(0);
                beanBreak.setCredit(dayTrackValue);
                beanBreak.setSubject_id(0);
                allocatedFacultyList.add(beanBreak);

                List<TableBean> usedWorkload = createTimetableDao.getUsedWorkload(sem_id, allocatedFacultyList);

                request.setAttribute("Monday", Monday);
                request.setAttribute("Tuesday", Tuesday);
                request.setAttribute("Wednesday", Wednesday);
                request.setAttribute("Thursday", Thursday);
                request.setAttribute("Friday", Friday);
                request.setAttribute("Saturday", Saturday);
                request.setAttribute("Sunday", Sunday);
                request.setAttribute("subjectList", subjectList);
                session.setAttribute("allocatedFacultyList", allocatedFacultyList);
                request.setAttribute("totalClass", totalClass);
                request.setAttribute("sizeCol", sizeCol);
                request.setAttribute("dayTrackValue", dayTrackValue);
                request.setAttribute("track", track);
                request.setAttribute("div_id", div_id);
                request.setAttribute("start_time", forwardStartTime);

                RequestDispatcher rd = request.getRequestDispatcher("/createTimetable.jsp");
                rd.forward(request, response);
            } else if (request.getRequestURI().contains("saveTimeTable")) {
                int totalHourSlot = Integer.parseInt(request.getParameter("lastHour"));
                int div_id = Integer.parseInt(request.getParameter("div_id"));
                int start_time = Integer.parseInt(request.getParameter("start_time"));
                int forwardStartTime = start_time;
                int startTime[] = new int[totalHourSlot + 1];
                int endTime[] = new int[totalHourSlot + 1];

                String Monday = request.getParameter("1");
                String Tuesday = request.getParameter("2");
                String Wednesday = request.getParameter("3");
                String Thursday = request.getParameter("4");
                String Friday = request.getParameter("5");
                String Saturday = request.getParameter("6");
                String Sunday = request.getParameter("7");

                TableBean bean = new TableBean();
                bean.setDiv_id(div_id);

                for (int i = 1; i <= totalHourSlot; i++) {
                    if (start_time <= 12) {
                        startTime[i] = start_time;
                        start_time++;
                        if (start_time <= 12) {
                            endTime[i] = start_time;
                        } else {
                            endTime[i] = 1;
                        }
                    } else {
                        start_time = 1;
                        startTime[i] = start_time;
                        start_time++;
                        endTime[i] = start_time;
                    }
                }

                int timetable_id = createTimetableDao.finalEntryMainTimeTable();

                bean.setTimetable_id(timetable_id);

                String FASI[] = new String[totalHourSlot + 1];
                String tempName;
                String tempNo;

                if ("true".equals(Monday)) {

                    bean.setDay_id(1);

                    for (int i = 1; i <= totalHourSlot; i++) {
                        tempName = "Monday_" + i + "_FASI";
                        tempNo = request.getParameter(tempName);

                        FASI[i] = tempNo;
                    }
                    createTimetableDao.insertTimetablemain(bean, totalHourSlot, FASI, startTime, endTime);
                }
                if ("true".equals(Tuesday)) {

                    bean.setDay_id(2);

                    for (int i = 1; i <= totalHourSlot; i++) {
                        tempName = "Tuesday_" + i + "_FASI";
                        tempNo = request.getParameter(tempName);

                        FASI[i] = tempNo;
                    }
                    createTimetableDao.insertTimetablemain(bean, totalHourSlot, FASI, startTime, endTime);

                }
                if ("true".equals(Wednesday)) {

                    bean.setDay_id(3);

                    for (int i = 1; i <= totalHourSlot; i++) {
                        tempName = "Wednesday_" + i + "_FASI";
                        tempNo = request.getParameter(tempName);

                        FASI[i] = tempNo;
                    }
                    createTimetableDao.insertTimetablemain(bean, totalHourSlot, FASI, startTime, endTime);

                }
                if ("true".equals(Thursday)) {

                    bean.setDay_id(4);

                    for (int i = 1; i <= totalHourSlot; i++) {
                        tempName = "Thursday_" + i + "_FASI";
                        tempNo = request.getParameter(tempName);

                        FASI[i] = tempNo;
                    }
                    createTimetableDao.insertTimetablemain(bean, totalHourSlot, FASI, startTime, endTime);

                }
                if ("true".equals(Friday)) {

                    bean.setDay_id(5);

                    for (int i = 1; i <= totalHourSlot; i++) {
                        tempName = "Friday_" + i + "_FASI";
                        tempNo = request.getParameter(tempName);

                        FASI[i] = tempNo;
                    }
                    createTimetableDao.insertTimetablemain(bean, totalHourSlot, FASI, startTime, endTime);

                }
                if ("true".equals(Saturday)) {

                    bean.setDay_id(6);

                    for (int i = 1; i <= totalHourSlot; i++) {
                        tempName = "Saturday_" + i + "_FASI";
                        tempNo = request.getParameter(tempName);

                        FASI[i] = tempNo;
                    }
                    createTimetableDao.insertTimetablemain(bean, totalHourSlot, FASI, startTime, endTime);

                }
                if ("true".equals(Sunday)) {

                    bean.setDay_id(7);

                    for (int i = 1; i <= totalHourSlot; i++) {
                        tempName = "Sunday_" + i + "_FASI";
                        tempNo = request.getParameter(tempName);

                        FASI[i] = tempNo;
                    }
                    createTimetableDao.insertTimetablemain(bean, totalHourSlot, FASI, startTime, endTime);

                }
                createTimetableDao.entryTimetableTrack(div_id, timetable_id, forwardStartTime, totalHourSlot);

                request.setAttribute("msg","msg");
                RequestDispatcher rd = request.getRequestDispatcher("/timetable.jsp");
                rd.forward(request, response);

            } else if (request.getRequestURI().contains("showTimeTable")) {

                int div_id = Integer.parseInt(request.getParameter("loadDivision"));
                int sem_id = Integer.parseInt(request.getParameter("semester"));
                List<UserBean> divTimeTable = createTimetableDao.divTimetable(div_id);

                request.setAttribute("divTimeTable", divTimeTable);
                request.setAttribute("sem_id", sem_id);
                RequestDispatcher rd = request.getRequestDispatcher("/selectTimeTable.jsp");
                rd.forward(request, response);
            } else if (request.getRequestURI().contains("selectMainTimeTable")) {

                int sem_id = Integer.parseInt(request.getParameter("semester"));
                int timetable_id = Integer.parseInt(request.getParameter("timetable_id"));
                
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+timetable_id);
                
                String Monday = null, Tuesday = null, Wednesday = null, Thursday = null, Friday = null, Saturday = null, Sunday = null;

                List<TableBean> timetableMain = createTimetableDao.loadTimeTableMain(timetable_id);
                List<TableBean> days = createTimetableDao.totalDays(timetable_id);

                List<TableBean> MonList = new ArrayList<>();
                List<TableBean> TueList = new ArrayList<>();
                List<TableBean> WedList = new ArrayList<>();
                List<TableBean> ThuList = new ArrayList<>();
                List<TableBean> FriList = new ArrayList<>();
                List<TableBean> SatList = new ArrayList<>();
                List<TableBean> SunList = new ArrayList<>();

                List<TableBean> MainList = new ArrayList<>();

                TableBean tableBean;
                TableBean tableTemp;

                tableBean = new TableBean();
                tableBean.setSem_id(sem_id);

                List<TableBean> subjectList = createTimetableDao.getSubjectList(tableBean);
                List<TableBean> allocatedFacultyList = createTimetableDao.getFacultyList(subjectList);

                //Get vertical data from horizontal panel
                for (int i = 0; i < timetableMain.size(); i++) {
                    tableBean = new TableBean();
                    tableTemp = new TableBean();

                    tableBean = timetableMain.get(i);

                    if (tableBean.getDay_id() == 1) {
                        tableTemp.setFaculty_allocation_subject_id(tableBean.getFaculty_allocation_subject_id());
                        MonList.add(tableTemp);
                    } else if (tableBean.getDay_id() == 2) {
                        tableTemp.setFaculty_allocation_subject_id(tableBean.getFaculty_allocation_subject_id());
                        TueList.add(tableTemp);
                    } else if (tableBean.getDay_id() == 3) {
                        tableTemp.setFaculty_allocation_subject_id(tableBean.getFaculty_allocation_subject_id());
                        WedList.add(tableTemp);
                    } else if (tableBean.getDay_id() == 4) {
                        tableTemp.setFaculty_allocation_subject_id(tableBean.getFaculty_allocation_subject_id());
                        ThuList.add(tableTemp);
                    } else if (tableBean.getDay_id() == 5) {
                        tableTemp.setFaculty_allocation_subject_id(tableBean.getFaculty_allocation_subject_id());
                        FriList.add(tableTemp);
                    } else if (tableBean.getDay_id() == 6) {
                        tableTemp.setFaculty_allocation_subject_id(tableBean.getFaculty_allocation_subject_id());
                        SatList.add(tableTemp);
                    } else if (tableBean.getDay_id() == 7) {
                        tableTemp.setFaculty_allocation_subject_id(tableBean.getFaculty_allocation_subject_id());
                        SunList.add(tableTemp);
                    }
                }

                //set days active and deacitve
                for (int i = 0; i < days.size(); i++) {
                    tableBean = new TableBean();

                    tableBean = days.get(i);
                    if (tableBean.getDays() == 1) {
                        Monday = "Monday";
                    } else if (tableBean.getDays() == 2) {
                        Tuesday = "Tuesday";
                    } else if (tableBean.getDays() == 3) {
                        Wednesday = "Wednesday";
                    } else if (tableBean.getDays() == 4) {
                        Thursday = "Thursday";
                    } else if (tableBean.getDays() == 5) {
                        Friday = "Friday";
                    } else if (tableBean.getDays() == 6) {
                        Saturday = "Saturday";
                    } else if (tableBean.getDays() == 7) {
                        Sunday = "Sunday";
                    }
                }

                List<UserBean> timeTableDetail = createTimetableDao.divTimetableDetail(timetable_id);

                UserBean userBean = timeTableDetail.get(0);
                int start_time = userBean.getStart_time();
                int total_duration = userBean.getTotal_duration();

                int sizeCol = 620 / total_duration;

                int startTime[] = new int[total_duration];
                int endTime[] = new int[total_duration];

                for (int i = 0; i < total_duration; i++) {
                    if (start_time <= 12) {
                        startTime[i] = start_time;
                        start_time++;
                        if (start_time <= 12) {
                            endTime[i] = start_time;
                        } else {
                            endTime[i] = 1;
                        }
                    } else {
                        start_time = 1;
                        startTime[i] = start_time;
                        start_time++;
                        endTime[i] = start_time;
                    }
                }

                // feel empty data into deactive days
                for (int i = 0; i < total_duration; i++) {
                    tableBean = new TableBean();

                    if (!"Monday".equals(Monday)) {
                        MonList.add(tableBean);
                    }
                    if (!"Tuesday".equals(Tuesday)) {
                        TueList.add(tableBean);
                    }
                    if (!"Wednesday".equals(Wednesday)) {
                        WedList.add(tableBean);
                    }
                    if (!"Thursday".equals(Thursday)) {
                        ThuList.add(tableBean);
                    }
                    if (!"Friday".equals(Friday)) {
                        FriList.add(tableBean);
                    }
                    if (!"Saturday".equals(Saturday)) {
                        SatList.add(tableBean);
                    }
                    if (!"Sunday".equals(Sunday)) {
                        SunList.add(tableBean);
                    }
                }

                for (int i = 0; i < total_duration; i++) {
                    tableBean = new TableBean();
                    tableTemp = new TableBean();

                    tableTemp = MonList.get(i);
                    tableBean.setMon(tableTemp.getFaculty_allocation_subject_id());

                    tableTemp = TueList.get(i);
                    tableBean.setTue(tableTemp.getFaculty_allocation_subject_id());

                    tableTemp = WedList.get(i);
                    tableBean.setWed(tableTemp.getFaculty_allocation_subject_id());

                    tableTemp = ThuList.get(i);
                    tableBean.setThu(tableTemp.getFaculty_allocation_subject_id());

                    tableTemp = FriList.get(i);
                    tableBean.setFri(tableTemp.getFaculty_allocation_subject_id());

                    tableTemp = SatList.get(i);
                    tableBean.setSat(tableTemp.getFaculty_allocation_subject_id());

                    tableTemp = SunList.get(i);
                    tableBean.setSun(tableTemp.getFaculty_allocation_subject_id());

                    tableBean.setStart(String.valueOf(startTime[i]));
                    tableBean.setEnd(String.valueOf(endTime[i]));

                    MainList.add(tableBean);
                }

                request.setAttribute("Monday", Monday);
                request.setAttribute("Tuesday", Tuesday);
                request.setAttribute("Wednesday", Wednesday);
                request.setAttribute("Thursday", Thursday);
                request.setAttribute("Friday", Friday);
                request.setAttribute("Saturday", Saturday);
                request.setAttribute("Sunday", Sunday);

                request.setAttribute("allocatedFacultyList", allocatedFacultyList);
                request.setAttribute("MainList", MainList);
                request.setAttribute("sizeCol", sizeCol);
                request.setAttribute("total_duration", total_duration);
                RequestDispatcher rd = request.getRequestDispatcher("/selectMainTimeTable.jsp");

                rd.forward(request, response);
            }
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
