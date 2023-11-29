import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

abstract class Booking {
    private String movieName;
    private String showtime;
    private int numberOfSeats;
    private double totalCost;
    private Date bookingDate;

    public Booking(String movieName, String showtime, int numberOfSeats, double totalCost) {
        this.movieName = movieName;
        this.showtime = showtime;
        this.numberOfSeats = numberOfSeats;
        this.totalCost = totalCost;
        this.bookingDate = new Date();
    }

    public String getMovieName() {
        return movieName;
    }

    public String getShowtime() {
        return showtime;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public abstract String getBookingType();
}

class MovieTicketBooking extends Booking {
    public MovieTicketBooking(String movieName, String showtime, int numberOfSeats, double totalCost) {
        super(movieName, showtime, numberOfSeats, totalCost);
    }

    @Override
    public String getBookingType() {
        return "Movie Ticket";
    }
}

public class MovieTicketBookingSystemWithHistoryAutomaticPaymenttest {
    private static JFrame loginFrame;
    private static ArrayList<Booking> bookingHistory = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowHomePageGUI();
        });
    }

    private static void createAndShowHomePageGUI() {
        JFrame frame = new JFrame("Movie Ticket Booking System - Home Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.PINK);

        JLabel welcomeLabel = new JLabel("Welcome to Movie Ticket Booking System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton bookTicketsButton = new JButton("Book Tickets");
        bookTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBookingWindow(frame);
            }
        });

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginWindow();
            }
        });

        JButton viewHistoryButton = new JButton("View History");
        viewHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBookingHistory();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bookTicketsButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(viewHistoryButton);

        frame.add(welcomeLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void openLoginWindow() {
        loginFrame = new JFrame("Movie Ticket Booking System - Login");
        loginFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        loginFrame.setSize(800, 400);
        loginFrame.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (authenticateUser(username, password)) {
                    JOptionPane.showMessageDialog(loginFrame, "Login successful! Proceed to ticket booking.");
                    openBookingWindow(loginFrame);
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Login failed. Please check your credentials.");
                }

                passwordField.setText("");
            }
        });

        loginFrame.setVisible(true);
    }

    private static boolean authenticateUser(String username, String password) {
     
        return true; 
    }

    private static void openBookingWindow(JFrame previousFrame) {
        previousFrame.setVisible(false);

        JFrame frame = new JFrame("Movie Ticket Booking System - Book Ticket");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new GridLayout(6, 2));

        JLabel movieLabel = new JLabel("Select Movie:");
        String[] movieOptions = {"Super30", "Shershaah", "Dhadak"};
        JComboBox<String> movieComboBox = new JComboBox<>(movieOptions);

        JLabel showtimeLabel = new JLabel("Select Showtime:");
        String[] showtimeOptions = {"10:00 AM", "2:00 PM", "6:00 PM"};
        JComboBox<String> showtimeComboBox = new JComboBox<>(showtimeOptions);

        JLabel seatsLabel = new JLabel("Number of Seats:");
        JTextField seatsField = new JTextField();

        JLabel totalCostLabel = new JLabel("Total Cost: Rs 0.00");

        JButton bookButton = new JButton("Book Ticket");

        frame.add(movieLabel);
        frame.add(movieComboBox);
        frame.add(showtimeLabel);
        frame.add(showtimeComboBox);
        frame.add(seatsLabel);
        frame.add(seatsField);
        frame.add(new JLabel());
        frame.add(totalCostLabel);
        frame.add(new JLabel());
        frame.add(bookButton);

        seatsField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTotalCost();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTotalCost();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTotalCost();
            }

            private void updateTotalCost() {
                try {
                    int numberOfSeats = Integer.parseInt(seatsField.getText());
                    double seatPrice = 150.0;
                    double totalCost = numberOfSeats * seatPrice;
                    totalCostLabel.setText("Total Cost: Rs " + String.format("%.2f", totalCost));
                } catch (NumberFormatException e) {
                    totalCostLabel.setText("Total Cost: Rs 0.00");
                }
            }
        });

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMovie = (String) movieComboBox.getSelectedItem();
                String selectedShowtime = (String) showtimeComboBox.getSelectedItem();
                int numberOfSeats = Integer.parseInt(seatsField.getText());

                double seatPrice = 150.0;
                double totalCost = numberOfSeats * seatPrice;

                MovieTicketBooking booking = new MovieTicketBooking(selectedMovie, selectedShowtime, numberOfSeats, totalCost);
                bookingHistory.add(booking);
                saveBookingHistoryToFile();

                JOptionPane.showMessageDialog(frame, "Ticket booked successfully. Total Cost: Rs " + String.format("%.2f", totalCost));

                frame.dispose();
                createAndShowPaymentGUI(totalCost);
            }
        });

        frame.setVisible(true);
    }

    private static void viewBookingHistory() {
        JFrame historyFrame = new JFrame("Booking History");
        historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        historyFrame.setSize(800, 400);

        JTextArea historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);

        for (Booking booking : bookingHistory) {
            historyTextArea.append("Booking Type: " + booking.getBookingType() + "\n");
            historyTextArea.append("Movie Name: " + booking.getMovieName() + "\n");
            historyTextArea.append("Showtime: " + booking.getShowtime() + "\n");
            historyTextArea.append("Number of Seats: " + booking.getNumberOfSeats() + "\n");
            historyTextArea.append("Booking Date: " + booking.getBookingDate() + "\n");
            historyTextArea.append("Total Cost: Rs " + String.format("%.2f", booking.getTotalCost()) + "\n\n");
        }

        JScrollPane scrollPane = new JScrollPane(historyTextArea);

        historyFrame.add(scrollPane);
        historyFrame.setVisible(true);
    }

    private static void saveBookingHistoryToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("booking_history.ser"));
            outputStream.writeObject(bookingHistory);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginFrame, "Error saving booking history to file.");
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadBookingHistoryFromFile() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("booking_history.ser"));
            bookingHistory = (ArrayList<Booking>) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            bookingHistory = new ArrayList<>();
        }
    }

    private static void createAndShowPaymentGUI(double totalCost) {
        JFrame frame = new JFrame("Movie Ticket Booking System - Payment Gateway");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.getContentPane().setBackground(new Color(194, 178, 128));
        frame.setLayout(new GridLayout(4, 2));

        JLabel paymentLabel = new JLabel("Select Payment Method:");
        String[] paymentOptions = {"Paytm", "Credit Card"};
        JComboBox<String> paymentComboBox = new JComboBox<>(paymentOptions);

        JLabel amountLabel = new JLabel("Enter Amount:");
        JTextField amountField = new JTextField();
        amountField.setText(String.format("%.2f", totalCost));

        JButton payButton = new JButton("Pay");

        frame.add(paymentLabel);
        frame.add(paymentComboBox);
        frame.add(amountLabel);
        frame.add(amountField);
        frame.add(new JLabel());
        frame.add(payButton);

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPaymentMethod = (String) paymentComboBox.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());

                PaymentGateway paymentGateway;
                if (selectedPaymentMethod.equals("Paytm")) {
                    paymentGateway = new PaytmGateway();
                } else {
                    paymentGateway = new CreditCardGateway();
                }

                if (paymentGateway.processPayment(amount)) {
                    savePaymentDetails(selectedPaymentMethod, amount);
                    JOptionPane.showMessageDialog(frame, "Payment successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Payment failed. Please try again.");
                }
            }
        });

        frame.setVisible(true);
    }

    private static void savePaymentDetails(String paymentMethod, double amount) {
        try (FileWriter writer = new FileWriter("payment_details.txt", true)) {
            writer.write("Payment Method: " + paymentMethod + "\n");
            writer.write("Amount: Rs" + amount + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

abstract class PaymentGateway {
    private String paymentProvider;

    public PaymentGateway(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public abstract boolean processPayment(double amount);

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }
}

class PaytmGateway extends PaymentGateway {
    public PaytmGateway() {
        super("Paytm");
    }

    @Override
    public boolean processPayment(double amount) {
        JOptionPane.showMessageDialog(null, "Payment of Rs" + amount + " processed through Paytm.");
        return true;
    }
}

class CreditCardGateway extends PaymentGateway {
    public CreditCardGateway() {
        super("Credit Card");
    }

    @Override
    public boolean processPayment(double amount) {
        JOptionPane.showMessageDialog(null, "Payment of Rs" + amount + " processed through Credit Card.");
        return true;
    }
}
