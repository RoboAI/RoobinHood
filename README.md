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
    
