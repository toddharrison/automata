//package com.eharrison.game.battle.a;
//
//import com.eharrison.game.battle.action.MoveAction;
//import com.eharrison.game.battle.action.WaitAction;
//import com.eharrison.game.battle.automata.Automata;
//import com.eharrison.game.battle.automata.AutomataState;
//import com.eharrison.game.battle.automata.Sensor;
//import com.eharrison.game.battle.brain.AutomataBrain;
//import com.eharrison.game.battle.game.Game;
//import com.eharrison.game.battle.game.GameState;
//import com.eharrison.game.battle.game.Vector2D;
//import com.eharrison.game.battle.game.Zone;
//import lombok.val;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Map;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class TestTest {
//    private @Mock AutomataBrain brain1;
//    private @Mock AutomataBrain brain2;
//
//    @Nested
//    public class GameTest {
//        @Test
//        public void call() {
//            val zone = new Zone(20, 10);
//            val sensor = new Sensor(4, 5);
//            val automata1 = new Automata(UUID.randomUUID(), brain1, sensor);
//            val automata2 = new Automata(UUID.randomUUID(), brain2, sensor);
//            val automata = Map.of(
//                    automata1, new AutomataState(new Vector2D(5, 5), new double[1][1], new byte[1], 10),
//                    automata2, new AutomataState(new Vector2D(6, 5), new double[1][1], new byte[1], 10)
//            );
//            val round = 0;
//            val gameState = new GameState(zone, automata, signals, round);
//
//            val game = new Game();
//
//            when(brain1.process(any())).thenReturn(new WaitAction());
//            when(brain2.process(any())).thenReturn(new MoveAction(0.0, 1));
//
//            val newState = game.runRound(gameState);
//
//            System.out.println(newState.automata().get(automata1));
//            System.out.println(newState.automata().get(automata2));
//        }
//    }
//
//    @Nested
//    public class SensorTest {
//        private Sensor sensor;
//
//        @BeforeEach
//        public void setup() {
//            sensor = new Sensor(4, 5);
//        }
//
//        @Nested
//        public class AngleToSectorTest {
//            @Test
//            public void call() {
//                assertEquals(0, sensor.angleToSector(0));
//                assertEquals(0, sensor.angleToSector(Math.PI * 0.25));
//                assertEquals(1, sensor.angleToSector(Math.PI * 0.5));
//                assertEquals(1, sensor.angleToSector(Math.PI * 0.75));
//                assertEquals(2, sensor.angleToSector(Math.PI));
//                assertEquals(2, sensor.angleToSector(Math.PI * 1.25));
//                assertEquals(3, sensor.angleToSector(Math.PI * 1.5));
//                assertEquals(3, sensor.angleToSector(Math.PI * 1.75));
//                assertEquals(0, sensor.angleToSector(Math.PI * 2.0));
//            }
//        }
//    }
//
//    @Nested
//    public class Vector2DTest {
//        @Nested
//        public class NormalizeAngleTest {
//            @Test
//            public void call() {
//                assertEquals(0, Vector2D.normalizeAngle(-Math.PI * 2));
//                assertEquals(Math.PI * 0.5, Vector2D.normalizeAngle(-Math.PI * 1.5));
//                assertEquals(Math.PI, Vector2D.normalizeAngle(-Math.PI));
//                assertEquals(Math.PI * 1.5, Vector2D.normalizeAngle(-Math.PI * 0.5));
//                assertEquals(0, Vector2D.normalizeAngle(0));
//                assertEquals(Math.PI * 0.5, Vector2D.normalizeAngle(Math.PI * 0.5));
//                assertEquals(Math.PI, Vector2D.normalizeAngle(Math.PI));
//                assertEquals(Math.PI * 1.5, Vector2D.normalizeAngle(Math.PI * 1.5));
//                assertEquals(0, Vector2D.normalizeAngle(Math.PI * 2));
//                assertEquals(Math.PI * 0.5, Vector2D.normalizeAngle(Math.PI * 2.5));
//                assertEquals(Math.PI, Vector2D.normalizeAngle(Math.PI * 3));
//            }
//        }
//
//        @Nested
//        public class AngleToTest {
//            @Test
//            public void callFromOrigin() {
//                assertEquals(0, new Vector2D(0, 0).angleTo(new Vector2D(0, 0)));
//
//                assertEquals(0, new Vector2D(0, 0).angleTo(new Vector2D(1, 0)));
//                assertEquals(Math.PI * 0.25, new Vector2D(0, 0).angleTo(new Vector2D(1, 1)));
//                assertEquals(Math.PI * 0.5, new Vector2D(0, 0).angleTo(new Vector2D(0, 1)));
//                assertEquals(Math.PI * 0.75, new Vector2D(0, 0).angleTo(new Vector2D(-1, 1)), 0.0001);
//                assertEquals(Math.PI, new Vector2D(0, 0).angleTo(new Vector2D(-1, 0)));
//                assertEquals(Math.PI * 1.25, new Vector2D(0, 0).angleTo(new Vector2D(-1, -1)));
//                assertEquals(Math.PI * 1.5, new Vector2D(0, 0).angleTo(new Vector2D(0, -1)));
//                assertEquals(Math.PI * 1.75, new Vector2D(0, 0).angleTo(new Vector2D(1, -1)));
//            }
//
//            @Test
//            public void call() {
//                assertEquals(0, new Vector2D(5, 5).angleTo(new Vector2D(5, 5)));
//
//                assertEquals(0, new Vector2D(5, 5).angleTo(new Vector2D(6, 5)));
//                assertEquals(Math.PI * 0.25, new Vector2D(5, 5).angleTo(new Vector2D(6, 6)));
//                assertEquals(Math.PI * 0.5, new Vector2D(5, 5).angleTo(new Vector2D(5, 6)));
//                assertEquals(Math.PI * 0.75, new Vector2D(5, 5).angleTo(new Vector2D(4, 6)), 0.0001);
//                assertEquals(Math.PI, new Vector2D(5, 5).angleTo(new Vector2D(4, 5)));
//                assertEquals(Math.PI * 1.25, new Vector2D(5, 5).angleTo(new Vector2D(4, 4)));
//                assertEquals(Math.PI * 1.5, new Vector2D(5, 5).angleTo(new Vector2D(5, 4)));
//                assertEquals(Math.PI * 1.75, new Vector2D(5, 5).angleTo(new Vector2D(6, 4)));
//            }
//        }
//
//        @Nested
//        public class ClampTest {
//            @Test
//            public void call() {
//                assertEquals(new Vector2D(0, 0), new Vector2D(-1, -1).clamp(0, 1, 0, 1));
//                assertEquals(new Vector2D(0, 1), new Vector2D(-1, 2).clamp(0, 1, 0, 1));
//                assertEquals(new Vector2D(1, 0), new Vector2D(2, -1).clamp(0, 1, 0, 1));
//                assertEquals(new Vector2D(1, 1), new Vector2D(2, 2).clamp(0, 1, 0, 1));
//            }
//        }
//    }
//}
