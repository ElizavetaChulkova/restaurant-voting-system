package ru.chulkova.restaurantvoting;

import ru.chulkova.restaurantvoting.HasId;

public interface HasIdAndEmail extends HasId {
    String getEmail();
}
