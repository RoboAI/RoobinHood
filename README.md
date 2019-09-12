# RoobinHood
RoobinHood

A simple android app with an Bow & Arrow and some physics..

All drawable objects are derived from GameObject; Bow, Arrow, Sun, etc..

Starting from Level1.java should give an insite of how code is linked together

A Scene (menu, level1, etc) handles all drawing of the above objects:

  E.g the class Level1 has the following:
  
    class Level1 extends GameScene...{
    ...
      private SceneBackground background;
      private Ground ground;
      private TheSun sun;
      private Bow bow;
      private StaticArrow staticArrow;
      private BowString bowString;
      private ArrowsThrower arrowsThrower;
      private FingerLine fingerLine;
      private Stars stars;
    }
    ```
    
<img src="https://zk7rma.am.files.1drv.com/y4mhvYQm34Tx_8s0ci2dpShu5Bv_chaA69d0ez7lC1eNwplCB-QC6stqYnk_wpgMYbWszGSaJOGqhuLXs6d7P7hasX8UH0eibHu4M3289GO9cOJzQIbt7A2QECBaF69nhd3ae63aQpKX3_ySVcXzDneDJR2rZwuJzZC1Is7TKBuoyHobPij0CMDuhnAgLaetMrKG4Fo51XHO8OxO1W5y4N-4Q?width=1024&height=493&cropmode=none" width="500">
    
