package com.iberianmotorsports.service.model.parsing;

import lombok.Data;

import java.util.List;

@Data
public class EntryList {
    private List<Entry> entries;
    private Float forceEntryList;
}
