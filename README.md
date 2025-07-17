# 🧱 BlockKit ![🛠](https://img.shields.io/badge/Minecraft-Toolkit-green?logo=github)

Modulaire toolkit voor Minecraft-plugins op Spigot. Fluente builders, GUI DSLs, config wrappers, en meer.  
Sinds `v1.0.1` stel je ChatConfig pas **na** `BlockKit.init(...)` in.

---

## 🚀 Installatie (Maven + JitPack)

```xml
<dependency>
  <groupId>com.github.DexZeeuw</groupId>
  <artifactId>BlockKit</artifactId>
  <version>1.0.1</version>
</dependency>
```

```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>
```

---

## 🧩 Voorbeeldgebruik

```java
@Override
public void onEnable() {
    BlockKit.init(this); // init core systems

    ChatConfig cfg = new ChatConfig(this);
    cfg.setPrefix("&7[DemoPlugin] &r");
    cfg.setMultiLine(true);
    BlockKit.setChatConfig(cfg); // activeer chat subsystem

    BlockKit.getChat().broadcast("Plugin is live!");
}
```

---

## 📦 Modules

| Module       | Functie                                     |
|--------------|---------------------------------------------|
| `ChatKit`    | Kleuren, hover/click, multi-line, gradients |
| `ItemKit`    | Fluente `ItemStack` builder                 |
| `MenuKit`    | Inventory GUIs, DSL, click-handlers         |
| `ConfigKit`  | YAML DSL, auto-save, resource loaders       |
| `TimeKit`    | Countdown/elapsed-formatters                |
| `FSKit`      | Copy, resource extractie, dir-utils         |
| `StringKit`  | `StringPipeline`: normalize, pad, accent    |
| `Extras`     | JSON-chat, PaginationBuilder, kleurutils    |

---

## 🛠 API voorbeelden

```java
ItemBuilder sword = BlockKit.itemBuilder(Material.DIAMOND_SWORD)
    .name("&bEpic Sword")
    .lore("&7A blade forged by stars", "&7Unbreakable &a+")
    .addEnchant(Enchantment.DAMAGE_ALL, 3);

MenuBuilder shop = BlockKit.menuBuilder("Shop", 3)
    .set(11, sword.build())
    .onClick(11, e -> e.getWhoClicked().sendMessage("You clicked a sword!"))
    .build();

BlockKit.getMenuManager().register(shop);
```

Voor texttransformatie:

```java
String result = StringPipeline.of("  Hé Rubén ")
    .stripAccents()
    .normalizeSpaces()
    .toLowerCase()
    .build(); // => "he ruben"
```

---

## 📘 Wiki en documentatie

🔗 [📘 Wiki](https://github.com/DexZeeuw/BlockKit/wiki)  
🔗 [📄 README.md](https://github.com/DexZeeuw/BlockKit#readme)

---

BlockKit vereist Java 17. Gecompileerd voor `Spigot 1.21.5-R0.1-SNAPSHOT`. Gedistribueerd via JitPack.
