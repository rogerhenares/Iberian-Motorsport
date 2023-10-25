package com.iberianmotorsports.service.model.parsing.export;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Entries {

    private List<BopEntry> entries = new ArrayList<BopEntry>();

}
