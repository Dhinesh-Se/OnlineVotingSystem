package com.kgisl.OnlineVotingSystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
//@WebFilter(urlPatterns = { "/polling" })
public class PollingFilter implements Filter {
    PollingDAO pollingDAO;
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        String voter_id = req.getParameter("voter_id");
        String election = req.getParameter("election");
        System.out.println(voter_id+" "+election);
        pollingDAO = PollingDAO.getInstance("jdbc:mysql://localhost:3306/votingsystem", "root", "");
        try {
            List<Polling> pollingList = pollingDAO.listAllPollings();
            pollingList.stream().forEach(System.out::println);
            for (Polling polling : pollingList) {
                if(polling.getVoter_id().equals(voter_id) && polling.getElection_name().equals(election))
                {
                    PrintWriter out = resp.getWriter();
                    resp.setContentType("text/html");
                    out.print("Vote already polled!!!");
                    RequestDispatcher rd = req.getRequestDispatcher("polling.html");
                    rd.include(req, resp);
                }
            }
            chain.doFilter(req, resp);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}