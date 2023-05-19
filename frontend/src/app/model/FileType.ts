export class TrackType {

    typeId: Number;

    name: String;

    constructor(typeId: Number, name: String) {
        this.typeId = typeId;
        this.name = name;
    }

    public static TRACK_NURBU(): TrackType {
        return new TrackType(1, 'NURBU');
    }

    public static TRACK_SPA(): TrackType {
        return new TrackType(2, 'SPA');
    }

}
