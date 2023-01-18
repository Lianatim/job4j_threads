package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        String pattern = "Not found account by id = %d";
        Account src = getById(fromId).orElseThrow(() -> new IllegalStateException(String.format(pattern, fromId)));
        Account dest = getById(toId).orElseThrow(() -> new IllegalStateException(String.format(pattern, toId)));
        if (src.amount() >= amount) {
            update(new Account(fromId, src.amount() - amount));
            update(new Account(toId, dest.amount() + amount));
            rsl = true;
        }
        return rsl;
    }
}