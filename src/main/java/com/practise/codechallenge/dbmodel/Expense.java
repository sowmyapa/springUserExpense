package com.practise.codechallenge.dbmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="expense")
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @OneToOne(optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @Id
    @Column(name="expense_id")
    private String expenseId;

    @Column(name="expense_date")
    private Date exepenseDate;

    @Column(name="expense_amount")
    private double expenseAmount;

    @Column(name="expense_vat")
    private double expenseVat;

    @Column(name="expense_reason")
    private String expenseReason;

}
