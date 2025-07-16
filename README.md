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
  - Utils: `ItemStackUtils`, `EnchantmentUtils`, `NBTUtils`, `TagUtils`
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

### ItemBuilder

```java
ItemStack sword = ItemBuilder.of(Material.DIAMOND_SWORD)
    .name("Excalibur")
    .lore("Legendary blade", "Unbreakable")
    .enchant(Enchantment.DAMAGE_ALL, 5, true)
    .addTag(ItemTag.LEGENDARY)
    .build();
```

### MenuBuilder

```java
Inventory menu = MenuBuilder.of("Select Option", 3)
    .background(filler)
    .item(2, 5, specialItem)
    .onClick(2, 5, e -> e.getPlayer().sendMessage("Clicked it!"))
    .build();
player.openInventory(menu);
```

### ChatService

```java
BlockKit.getChat().messenger().send(player, "Welcome aboard!");
BlockKit.getChat().messenger().broadcast("&cServer restarts in 5 minutes!");
```

### ConfigBuilder

```java
ConfigFile mainCfg = ConfigBuilder.of(plugin)
    .fileName("config.yml")
    .autoSave(true)
    .register(BlockKit.getConfigService());

String mode = mainCfg.getConfiguration().getString("game.mode");
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
