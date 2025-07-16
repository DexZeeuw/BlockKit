# BlockKit

A modular Java utility & builder library for fast, maintainable Bukkit/Spigot plugin development. BlockKit bundles your most-used helpers into three layers—API contracts, core implementations and optional DSL extensions—so you can focus on features, not boilerplate.

---

## 📦 Modules

```text
blockkit/
├── blockkit-api    # Interfaces, contracts & value‐objects
├── blockkit-core   # Concrete implementations per feature
└── blockkit-extras # DSL‐extensions & advanced helpers
```

### blockkit-api  
Defines all public interfaces and service contracts with zero direct Bukkit/Spigot dependencies. Examples:

- `com.blockkit.api.ItemBuilder`  
- `com.blockkit.api.MenuBuilder`  
- `com.blockkit.chat.api.ChatMessenger` / `ChatFormatter`  
- `com.blockkit.config.api.ConfigFile`  
- `com.blockkit.time.api.DurationFormatter`

### blockkit-core  
Provides battle-tested implementations, organized by feature:

- **item**  
  - Builder: `ItemBuilder.of(...)`  
  - Utils: `ItemStackUtils`, `EnchantmentUtils`, `NBTUtils`
- **menu**  
  - `MenuBuilder.of(...)`, `BorderUtils`, `MenuManager`, `MenuListener`
- **text**  
  - Color: `ColorUtils`, `VersionUtils`, `ColorPattern`, `ColorPalette`  
  - Gradient: `GradientBuilder`, `GradientUtils`  
  - Chat: `ChatService`, `ChatConfig`, `ChatFormatterImpl`, `ChatMessengerImpl`
- **config**  
  - `YamlConfig`, `ConfigService`
- **fs**  
  - `DirectoryChecker`, `DirectoryCopier`, `DirectoryDeleter`, `FileRenamer`, `FsLogger`
- **string**  
  - Checks: `StringCheckUtils`  
  - Case: `StringCaseUtils`  
  - Substr: `StringSubstrUtils`, `StringBetweenUtils`  
  - Pad: `StringPadUtils`  
  - Strip: `StringStripUtils`  
  - Normalize: `StringNormalizeUtils`  
  - Search: `StringSearchUtils`  
  - Validate: `StringValidateUtils`
- **time**  
  - `DefaultDurationFormatter`, `TimeService`

### blockkit-extras  
Opt-in DSL and advanced modules:

- **Menu pagination**  
- **ChatComponentBuilder** (hover/click JSON‐chat)  
- **StringPipeline** (chain string transforms)  
- **ConfigBuilder** DSL  
- **DurationFormatterBuilder** (custom units/locales)

---

## 🚀 Quickstart

### Requirements

- Java 11+  
- Gradle 6+ or Maven 3.6+  
- Bukkit/Spigot 1.16+

### Clone & build

```bash
git clone https://github.com/DexZeeuw/BlockKit.git
cd BlockKit

# Gradle
./gradlew build

# or Maven
mvn clean install
```

---

## ✨ Usage Examples

### Initialize
```java
@Override
public void onEnable() {
    BlockKit.init(this);
    // nu kun je:
    // BlockKit.getChatService().send(player, "Welkom!");
    // BlockKit.getMenuManager().register(...);
    // BlockKit.getConfigService()….
    // BlockKit.getTimeService().until(...);
}
```

### Item bouwen

```java
// 1) Basis “legendary” zwaard maken en toevoegen aan speler-inventory
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

Player player = …;
ItemStack excalibur = BlockKit
    .itemBuilder(Material.DIAMOND_SWORD)
    .name("&6Excalibur")                             // goudkleurige naam
    .lore("&7Legendary sword", "&aUnbreakable")      // twee regels lore
    .enchant(Enchantment.DAMAGE_ALL, 5, true)        // Sharpness V
    .tag("rarity", "legendary")                      // in NBT opslaan
    .customModelData(101)                            // custom model data voor resourcepack
    .build();

player.getInventory().addItem(excalibur);
```

### Menu bouwen

```java
// menu bouwen
Inventory shop = BlockKit
    .menuBuilder("Equipment Shop", 3)
    .background(filler)
    .item(1, 1, chestplate)
    .item(1, 3, excalibur)
    .onClick(1, 1, e -> {
        Player clicker = (Player) e.getWhoClicked();
        clicker.getInventory().addItem(chestplate);
        clicker.closeInventory();
        BlockKit.getChat().send(clicker, "Je hebt de Guardian Chestplate gekocht!");
    })
    .onClick(1, 3, e -> {
        Player clicker = (Player) e.getWhoClicked();
        clicker.getInventory().addItem(excalibur);
        clicker.closeInventory();
        BlockKit.getChat().send(clicker, "Je hebt Excalibur gekocht!");
    })
    .build();

// registreer en open
BlockKit.getMenuManager().register(shop);
player.openInventory(shop);
```

### Pagination

```java
// Bereid een lange lijst items voor
List<ItemStack> shopItems = new ArrayList<>();
for (Material mat : Material.values()) {
    shopItems.add(
        BlockKit.itemBuilder(mat)
            .name(mat.name())
            .build()
    );
}

// Bouw een paginated menu (5 rijen hoog)
PaginationBuilder.Pagination menuPages = PaginationBuilder
    .of(shopItems, "Shop Items", 5)
    .build();

// Registreer alle pagina’s en open de eerste
menuPages.getPageCount(); // bijv. 10 pagina's
menuPages.open(player);
```

### Chats versturen

```java
BlockKit.getChat().messenger().send(player, "Welcome aboard!");
BlockKit.getChat().messenger().broadcast("&cServer restarts in 5 minutes!");
```

### Items & Chats

```java
// 4) Items in een andere context (bv. shop-koppeling)
public void giveStarterKit(Player p) {
    ItemStack sword = BlockKit.itemBuilder(Material.IRON_SWORD)
        .name("&bStarter Sword")
        .build();

    ItemStack pick  = BlockKit.itemBuilder(Material.IRON_PICKAXE)
        .name("&bStarter Pickaxe")
        .build();

    p.getInventory().addItem(sword, pick);
    BlockKit.getChat().send(p, "&aJe hebt je starterkit ontvangen!");
}
```

### Laden van je config

```java
// ────────────────────────────────────────────────────────────────
// 1) Laden van je config in onEnable()
// ────────────────────────────────────────────────────────────────

@Override
public void onEnable() {
    // 1. Init BlockKit
    BlockKit.init(this);

    // 2. DSL-stijl config registreren & automatisch opslaan bij shutdown
    ConfigFile mainCfg = ConfigBuilder.of(this)
        .fileName("config.yml")
        .autoSave(true)
        .register(BlockKit.getConfigService());

    // 3. Waarden uitlezen met defaults
    String welcome = mainCfg.getConfiguration()
        .getString("messages.welcome", "&aWelkom op de server, %player%!");
    int maxPlayers = mainCfg.getConfiguration()
        .getInt("game.maxPlayers", 50);

    // 4. Gebruik in je plugin
    BlockKit.getChat().broadcast(welcome.replace("%player%", "wereld"));
    getLogger().info("Maximum spelers ingesteld op " + maxPlayers);
}
```

### Waarde aanpassen & opslaan

```java
public void setMaxPlayers(int newMax) {
    ConfigFile cfg = BlockKit.getConfigService().get("config.yml");
    cfg.getConfiguration().set("game.maxPlayers", newMax);
    cfg.save();  // direct wegschrijven naar disk

    BlockKit.getChat().broadcast(
        "&eMaxPlayers aangepast naar " + newMax);
}
```

### Tijd gebruik

```java
// Basis countdown tot morgen 12:00
String untilNoon = BlockKit.getTimeService()
    .until(LocalDateTime.now()
        .plusDays(1)
        .withHour(12).withMinute(0));
BlockKit.getChat().broadcast("&eNog " + untilNoon + " tot morgen 12:00");

// Custom formatter met Nederlands en skip-zero=false
DurationFormatter custom = DurationFormatterBuilder.of()
    .locale(new Locale("nl"))
    .skipZeroUnits(false)
    .labelDays("dag", "dagen")
    .labelHours("uur", "uur")
    .build();

TimeService customService = new TimeService(custom);
String sinceEvent = customService.since(LocalDateTime.of(2025,7,16,9,0));
BlockKit.getChat().broadcast("&aSinds event: " + sinceEvent);
```

### StringPipeline

```java
String result = StringPipeline.of("  héllö wørld  ")
    .trim()
    .stripAccents()
    .normalizeSpace()
    .capitalizeAllWords()
    .center(20, '*')
    .build();
// "**Hello World****"
```

### DurationFormatter

```java
String countdown = BlockKit.getTimeService()
    .until(LocalDateTime.now().plusHours(3).plusMinutes(45));
// "3 uur 45 minuten"
```

### StringPipeline

```java
String raw = "  héllö   wørld  ";
String transformed = StringPipeline.of(raw)
    .trim()
    .stripAccents()
    .normalizeSpaces()
    .capitalizeAllWords()
    .center(20, '*')
    .build();

// transformed == "**Hello World****"
player.sendMessage(transformed);
```

### ChatComponent

```java
// A) Eenvoudig gekleurde tekst met hover
BaseComponent[] msg1 = ChatComponentBuilder
    .text("Klik ")
    .then("hier")
      .color(ChatColor.GOLD)
      .hoverText("Bezoek onze site")
      .onClickOpenUrl("https://example.com")
    .then(" voor meer info")
    .build();
player.spigot().sendMessage(msg1);
```

```java
// B) Tooltip met item en run‐command
ItemStack gift = BlockKit.itemBuilder(Material.CHEST)
    .name("&bMystery Box")
    .tag("loot", "epic")
    .build();

BaseComponent[] msg2 = ChatComponentBuilder
    .text("Ontvang je gift: ")
    .then("Mystery Box")
      .color(ChatColor.AQUA)
      .hoverItem(gift)
      .onClickRunCommand("/openbox")
    .build();
player.spigot().sendMessage(msg2);
```

### FS extras

```java
@Override
public void onEnable() {
    // Extract default map uit jar
    try {
        ResourceExtractor.extract(
            this, "default/maps/map1", 
            new File(getDataFolder(), "maps/map1"), false);
    } catch (IOException e) {
        getLogger().severe("Extract failed: " + e.getMessage());
    }

    // Backup map
    DirectoryCopier.copyDirectory(
        new File(getDataFolder(), "maps"), 
        new File(getDataFolder(), "maps_backup"));

    // Watch plugin folder voor wijzigingen
    try {
        DirWatcher watcher = DirWatcher.of(
            getDataFolder().toPath(), evt ->
                getLogger().info("FS change: " + evt.kind() + " -> " + evt.context()));
        watcher.start();
    } catch (IOException e) {
        e.printStackTrace();
    }

    // List datafolder contents
    List<File> files = FileUtils.list(getDataFolder());
    files.forEach(f -> getLogger().info("Found: " + f.getName()));
}
```

---

## 📑 Project Structure

```text
blockkit/
├── blockkit-api
│   └── src/main/java/com/blockkit/api/…
├── blockkit-core
│   └── src/main/java/com/blockkit/…
└── blockkit-extras
    └── src/main/java/com/blockkit/extras/…
```

---

## 📚 Documentation

- Javadoc: `./gradlew javadoc`  
- Examples & tutorials: see `/docs` folder  

---

## 🤝 Contributing

1. Fork the repo  
2. Create a feature branch  
3. Open a pull request  

Please run all unit tests before submitting.

---

## 📄 License

This project is MIT-licensed. See [LICENSE](LICENSE) for details.
