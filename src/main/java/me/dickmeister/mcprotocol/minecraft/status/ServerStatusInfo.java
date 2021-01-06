package me.dickmeister.mcprotocol.minecraft.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.md_5.bungee.api.chat.BaseComponent;

@Data
@AllArgsConstructor
public class ServerStatusInfo {

    private VersionInfo version;
    private PlayerInfo players;
    private BaseComponent[] description;
    private String icon;

    public String getDescriptionAsText() {
        return (description.length > 0
                ? description[0].toPlainText()
                : "unknown description"
        );
    }
}
