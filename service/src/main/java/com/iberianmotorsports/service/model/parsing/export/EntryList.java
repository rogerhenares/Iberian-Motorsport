package com.iberianmotorsports.service.model.parsing.export;

import com.iberianmotorsports.service.model.parsing.export.Entry;
import lombok.Data;

import java.util.List;

@Data
public class EntryList {
    private List<Entry> entries;
    private Float forceEntryList;
}
