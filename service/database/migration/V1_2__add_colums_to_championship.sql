ALTER TABLE CHAMPIONSHIP
ADD randomize_track_when_empty INT,
ADD central_entry_list_path VARCHAR(45),
ADD allow_auto_dq INT,
ADD short_formation_lap INT,
ADD dump_entry_list INT,
ADD formation_lap_type INT,
ADD ignore_premature_disconnects INT;
