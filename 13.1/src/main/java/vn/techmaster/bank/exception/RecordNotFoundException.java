package vn.techmaster.bank.exception;

public class RecordNotFoundException extends RuntimeException{
  public RecordNotFoundException(String table, String id) {
    super(String.format("Record with id : '%s' in table '%s' is not found", id, table));
  }
}
