package ed.lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class E01InvertBTTest {

    @ParameterizedTest
    @CsvFileSource(resources = "E01.csv", useHeadersInDisplayName = true, delimiter = '|')
    void invertTreeTest(String input, String expectedOutput) {
        final TreeNode<Integer> root = BTUtils.parseBT(input, Integer::parseInt);
        final TreeNode<Integer> expected = BTUtils.parseBT(expectedOutput, Integer::parseInt);

        final E01InvertBT test = new E01InvertBT();

        final TreeNode<Integer> result = test.invertTree(root);

        similarTrees(result, expected);
    }

    private void similarTrees(TreeNode<Integer> root1, TreeNode<Integer> root2) {
        if (root1 == null) {
            assertNull(root2,
                    String.format("Se esperaba un nodo null y se encontró [%s]", root2));
            return;
        }

        assertNotNull(root2,
                String.format("Se esperaba [%s] y se encontró un nodo nulo", root1));

        assertEquals(root1.value, root2.value,
                String.format("Se esperaba [%s] y se encontró [%s]", root1, root2));

        similarTrees(root1.left, root2.left);
        similarTrees(root1.right, root2.right);
    }
}