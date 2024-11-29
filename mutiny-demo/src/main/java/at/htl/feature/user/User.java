package at.htl.feature.user;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "MD_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "u_firstname", nullable = false)
    private String firstName;

    @Column(name = "u_lastname", nullable = false)
    private String lastName;

    @Column(name ="u_email", nullable = false)
    private String email;

    @Column(name ="u_date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "u_address", nullable = false)
    private String address;

    public User() {
    }

    public User(String firstName, String lastName, String email, LocalDate dateOfBirth, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
