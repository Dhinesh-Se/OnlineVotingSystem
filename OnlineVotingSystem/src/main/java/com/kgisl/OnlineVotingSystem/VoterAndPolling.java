package com.kgisl.OnlineVotingSystem;

public class VoterAndPolling {
    protected String voter_id;
    protected String name;
    protected String email;
    protected String password;
    protected int age;
    protected String gender;
    protected String ward;
    protected String party_name;
    protected String election_name;
    protected String election_date;
    public VoterAndPolling(String voter_id, String name, String email, String password, int age, String gender,
            String ward, String party_name, String election_name, String election_date) {
        this.voter_id = voter_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.ward = ward;
        this.party_name = party_name;
        this.election_name = election_name;
        this.election_date = election_date;
    }

    public String getElection_name() {
        return election_name;
    }

    public void setElection_name(String election_name) {
        this.election_name = election_name;
    }

    public String getElection_date() {
        return election_date;
    }

    public void setElection_date(String election_date) {
        this.election_date = election_date;
    }

    public String getVoter_id() {
        return voter_id;
    }

    public void setVoter_id(String voter_id) {
        this.voter_id = voter_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }

    @Override
    public String toString() {
        return "VoterAndPolling [voter_id=" + voter_id + ", name=" + name + ", email=" + email + ", password="
                + password + ", age=" + age + ", gender=" + gender + ", ward=" + ward + ", party_name=" + party_name
                + "]";
    }

}
