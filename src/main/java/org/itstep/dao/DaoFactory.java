package org.itstep.dao;


import org.itstep.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract PatientDao createPatientDao();
    public abstract DoctorDao createDoctorDao();
    public abstract MedicationLogDao createMedicationLogDao();
    public abstract UserDao createUserDao();
    public abstract HospitalListDao createHospitalListDao();
    public abstract NurseDao createNurseDao();

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
