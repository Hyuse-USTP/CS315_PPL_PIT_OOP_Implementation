# CS315_PPL_PIT_OOP_Implementation

### How the LRU Cache Implementation Works

This LRU Cache is designed for efficient storage and retrieval of data, maintaining optimal performance by using a combination of two core data structures:

1.  **Hash Map (`java.util.HashMap`):**
    *   **Purpose:** Provides **O(1)** (constant time) average-case complexity for lookups, insertions, and deletions. It acts as an "index" to quickly find any item in the cache using its key.
    *   **Functionality:** Each key in the `HashMap` maps directly to a `Node` object in our Doubly Linked List, allowing instant access to the actual cache entry.

2.  **Doubly Linked List:**
    *   **Purpose:** Maintains the order of item usage, also allowing **O(1)** operations for moving nodes.
    *   **Functionality:**
        *   The **head** of the list always points to the **Most Recently Used (MRU)** item.
        *   The **tail** of the list always points to the **Least Recently Used (LRU)** item.
        *   Because it's "doubly" linked (each node knows its previous and next node), we can quickly remove a node from any position and re-insert it at the head, maintaining the MRU order without a full list traversal.
    *   **Dummy Nodes:** We use special "dummy" `head` and `tail` nodes that don't store actual data. This simplifies the logic for adding and removing nodes, especially at the boundaries of the list, by eliminating many edge-case null checks.

**Operations Breakdown:**

*   **`get(key)`:**
    1.  The `HashMap` is queried to find the `Node` associated with the `key`.
    2.  If the node is found, it's considered "recently used," so it's efficiently moved to the **head** of the Doubly Linked List.
    3.  The item's value is returned.

*   **`put(key, value)`:**
    1.  The `HashMap` checks if the `key` already exists.
    2.  **If the key exists:** The existing node's value is updated, and the node is moved to the **head** of the list (as it's now recently used).
    3.  **If the key is new:** A new `Node` is created, added to the `HashMap`, and inserted at the **head** of the list.
    4.  **Capacity Check:** If adding this new item causes the cache to exceed its maximum capacity, a warning is issued (informing which LRU item will be evicted), and the **LRU item** (the one at the `tail` of the list) is removed from both the Doubly Linked List and the `HashMap`.

---

### How to Launch the Application

To run this LRU Cache application, follow these steps:

1.  **Prerequisite: Install Java Development Kit (JDK)**
    *   Ensure you have a **Java Development Kit (JDK)** installed (version 8 or newer). If the `javac` compiler (part of the JDK) is not found on your system, you will need to download and install the appropriate JDK for your operating system from a reputable source like Oracle (Oracle JDK) or OpenJDK (e.g., Adoptium).

2.  **Navigate to the Project Directory**
    *   Open your terminal or command prompt and navigate to the root directory of this project where `src` and `README.md` are located.

3.  **Compile the Java Source Code**
    *   This command will create a `bin` directory (if it doesn't exist) and compile the `.java` files from the `src` folder into `.class` files within `bin`.
        ```bash
        mkdir -p bin
        javac -d bin src/LRUCache.java src/Main.java
        ```

4.  **Run the Application**
    *   This command executes the compiled `Main` class.
        ```bash
        java -cp bin Main
        ```

Upon running, the application will present you with a main menu where you can choose between:
*   **Hardcoded Demo:** Runs the predefined sequence of operations from the project proposal.
*   **Interactive Mode:** Allows you to input `put`, `get`, and `print` commands manually.
*   **Exit:** Quits the application.

After completing a demo or interactive session, you will return to this main menu.
