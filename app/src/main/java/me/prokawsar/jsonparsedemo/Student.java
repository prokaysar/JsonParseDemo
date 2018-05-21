package me.prokawsar.jsonparsedemo;

class Student {
    String name;
    String dep;
    String cgpa;
    String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return String.format("Name : %s\nDep : %s\nCGPA : %s\nDesc : %s",name,dep,cgpa,desc);
    }
}
