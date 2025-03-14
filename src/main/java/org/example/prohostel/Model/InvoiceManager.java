package org.example.prohostel.Model;

import java.io.*;
import java.util.ArrayList;

public class InvoiceManager {
    private ArrayList<Invoice> invoices = new ArrayList<Invoice>();
    public InvoiceManager(){
        loadInvoicesFromFile();
    }


    public void addInvoices(Invoice invoice){
        invoices.add(invoice);
        System.out.println("Da them hoa don");
        saveInvoicesToFile();
    }

    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }
    // lay danh sach hoa don theo ten khach hang
    public ArrayList<Invoice> guestInvoices(String guestID){
        ArrayList<Invoice> listInvoices = new ArrayList<Invoice>();
        for(Invoice invoice: invoices){
            if(invoice.getGuest().getIDCard().equals(guestID)){
                listInvoices.add(invoice);
            }
        }
        return listInvoices;
    }
    // lay hoa don khach hang theo ten , thoi gian booking...
    public boolean guestInvoice(Invoice invoice){
        ArrayList<Booking>payedBooking1 = invoice.getPayedBookings();
        for(Invoice invc: invoices){
            if(invoice.getGuest().getIDCard().equals(invc.getGuest().getIDCard())){
                ArrayList<Booking> payedBoking2 = invc.getPayedBookings();
                if(payedBooking1.size() == payedBoking2.size() && payedBooking1.containsAll(payedBoking2)){
                    return true;
                }
            }
        }
        return false;
    }


    private void loadInvoicesFromFile(){
        File file = new File("Invoices.dat");
        if (!file.exists() || file.length() == 0) {
            invoices = new ArrayList<Invoice>();
            return;
        }
        try{
            FileInputStream fileInvoice = new FileInputStream("Invoices.dat");
            ObjectInputStream ois = new ObjectInputStream(fileInvoice);
            invoices = (ArrayList<Invoice>) ois.readObject();
            ois.close();
            fileInvoice.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    private void saveInvoicesToFile() {

        try {
            FileOutputStream fileInvoice = new FileOutputStream("Invoices.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fileInvoice);
            oos.writeObject(invoices);
            oos.close();
            fileInvoice.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeInvoice(Invoice invoice){
        invoices.remove(invoice);
        saveInvoicesToFile();
    }

}
