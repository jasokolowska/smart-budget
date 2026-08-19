package com.sokolowska.transactions.api;

import com.sokolowska.transactions.domain.model.Transaction;
import com.sokolowska.transactions.service.TransactionsService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

  private final TransactionsService transactionsService;

  public TransactionsController(TransactionsService transactionsService) {
    this.transactionsService = transactionsService;
  }

  @GetMapping
  public ResponseEntity<List<Transaction>> getTransactions() {
    return ResponseEntity.ok(transactionsService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
    return ResponseEntity.ok(transactionsService.getById(id));
  }

  @PostMapping
  public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {
    try {
      Long id = transactionsService.addTransaction(transaction);
      URI uri =
          ServletUriComponentsBuilder.fromCurrentRequestUri()
              .path("/{id}")
              .buildAndExpand(id)
              .toUri();
      return ResponseEntity.created(uri).build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
