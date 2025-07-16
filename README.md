# BlockKit

A modular Java utility and builder library for rapid, maintainable Bukkit/Spigot plugin development. BlockKit bundles your most-used helpers into three clear layers—API contracts, core implementations and optional DSL extensions—so you can focus on features, not boilerplate.

---

## 📦 Modules overview

| Module          | Purpose                                                                  |
| --------------- | ------------------------------------------------------------------------ |
| blockkit-api    | Public interfaces and service contracts, zero direct Bukkit dependencies |
| blockkit-core   | Battle-tested implementations organized by feature                      |
| blockkit-extras | Opt-in DSL extensions and advanced helpers                              |

---

## ⚙️ Requirements

- Java 11 or higher  
- Gradle 6+ or Maven 3.6+  
- Bukkit/Spigot 1.16+  

---

## 🚀 Quickstart

```bash
git clone https://github.com/DexZeeuw/BlockKit.git
cd BlockKit

# Gradle build
./gradlew build

# or Maven build
mvn clean install
```

In your plugin’s `onEnable()`:

```java
@Override
public void onEnable() {
    BlockKit.init(this);
}
```

---

## ✨ Core features

### ChatKit

Send formatted messages to players or console:

```java
BlockKit.getChat().send(player, "&aHello, world!");
BlockKit.getChat().broadcast("&cServer restarts in 5 minutes!");
```

### ItemKit

Fluent builder for `ItemStack` with name, lore, enchants, NBT tags, custom model data:

```java
ItemStack sword = BlockKit
  .itemBuilder(Material.DIAMOND_SWORD)
  .amount(1)
  .name("&6Excalibur")
  .lore("&7Legendary sword", "&aUnbreakable")
  .enchant(Enchantment.DAMAGE_ALL, 5, true)
  .tag("rarity", "legendary")
  .customModelData(101)
  .build();

player.getInventory().addItem(sword);
```

### MenuKit

Build and register interactive inventories with click handlers:

```java
Inventory menu = BlockKit
  .menuBuilder("Select Option", 3)
  .background(fillerItem)
  .item(1, 4, choiceItem)
  .onClick(1, 4, e -> {
      Player p = (Player) e.getWhoClicked();
      BlockKit.getChat().send(p, "You chose it!");
      p.closeInventory();
  })
  .build();

BlockKit.getMenuManager().register(menu);
player.openInventory(menu);
```

### ConfigKit

Load, save and query YAML files via a simple DSL:

```java
ConfigFile cfg = ConfigBuilder.of(this)
  .fileName("config.yml")
  .autoSave(true)
  .register(BlockKit.getConfigService());

String welcome = cfg.getConfiguration()
  .getString("messages.welcome", "&aWelcome, %player%!");
BlockKit.getChat().broadcast(welcome.replace("%player%", "Alice"));
```

### TimeKit

Format durations, countdowns and elapsed times:

```java
String untilNoon = BlockKit.getTimeService()
  .until(LocalDateTime.now().plusDays(1).withHour(12).withMinute(0));
// e.g. "23 hours 58 minutes"

DurationFormatter custom = DurationFormatterBuilder.of()
  .locale(new Locale("nl"))
  .skipZeroUnits(false)
  .labelDays("dag","dagen")
  .build();

String sinceEvent = custom.since(LocalDateTime.of(2025,7,16,9,0));
// e.g. "1 dag 6 uur 30 minuten"
```

---

## 🛠️ Extras (optional)

| Extra Kit               | Description                                                  |
| ----------------------- | ------------------------------------------------------------ |
| StringPipeline          | Chain multiple string transformations in a fluent API        |
| PaginationBuilder       | Create paginated GUIs with “Previous/Next” navigation        |
| ChatComponentBuilder    | Build JSON chat components with hover and click events       |
| FS-helpers              | Filesystem utilities: copy, delete, extract resources, watch |

---

## 📁 Project structure

```text
blockkit/
├── blockkit-api
│   └── src/main/java/com/blockkit/api/…
├── blockkit-core
│   └── src/main/java/com/blockkit/…
└── blockkit-extras
    └── src/main/java/com/blockkit/…
```

---

## 🤝 Contributing

1. Fork the repository  
2. Create a feature branch  
3. Run all unit tests (`./gradlew test` or `mvn test`)  
4. Submit a pull request  

---

## 📄 License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.
