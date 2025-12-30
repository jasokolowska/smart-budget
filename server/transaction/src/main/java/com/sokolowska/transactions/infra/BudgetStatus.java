package com.sokolowska.transactions.infra;

public enum BudgetStatus {
  DRAFT, // Szkic tworzony ręcznie
  AI_PROPOSAL, // Wygenerowany przez GPT-4o, czeka na akceptację (sanity-check)
  ACTIVE, // Zatwierdzony i obowiązujący
  ARCHIVED // Historyczny
}
