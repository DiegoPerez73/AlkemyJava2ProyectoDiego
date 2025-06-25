package com.Java2.proyecto.diego.AlkemyDiego.repository;

  import com.Java2.proyecto.diego.AlkemyDiego.model.User;
  import com.google.api.core.ApiFuture;
  import com.google.cloud.firestore.*;
  import org.junit.jupiter.api.BeforeEach;
  import org.junit.jupiter.api.Test;
  import org.junit.jupiter.api.extension.ExtendWith;
  import org.mockito.*;

  import java.util.List;
  import java.util.Optional;

  import static org.junit.jupiter.api.Assertions.*;
  import static org.mockito.Mockito.*;

  @ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
  class UserRepositoryTest {

    @Mock
    private Firestore firestore;
    @Mock
    private CollectionReference collectionReference;
    @Mock
    private DocumentReference documentReference;
    @Mock
    private ApiFuture<QuerySnapshot> querySnapshotApiFuture;
    @Mock
    private QuerySnapshot querySnapshot;
    @Mock
    private DocumentSnapshot documentSnapshot;
    @Mock
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    @Mock
    private Query query;
    @Mock
    private QueryDocumentSnapshot queryDocumentSnapshot;

    @InjectMocks
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
      user = User.builder()
          .id("1")
          .username("testuser")
          .name("Test User")
          .password("pass")
          .build();
    }

    @Test
    void testFindByUsername_found() throws Exception {
      when(firestore.collection("users")).thenReturn(collectionReference);
      when(collectionReference.whereEqualTo("username", "testuser")).thenReturn(query);
      when(query.limit(1)).thenReturn(query);
      when(query.get()).thenReturn(querySnapshotApiFuture);
      when(querySnapshotApiFuture.get()).thenReturn(querySnapshot);
      doReturn(List.of(queryDocumentSnapshot)).when(querySnapshot).getDocuments();
      when(queryDocumentSnapshot.toObject(User.class)).thenReturn(user);

      Optional<User> result = userRepository.findByUsername("testuser");

      assertTrue(result.isPresent());
      assertEquals(user, result.get());
    }

    @Test
    void testFindByUsername_notFound() throws Exception {
      when(firestore.collection("users")).thenReturn(collectionReference);
      when(collectionReference.whereEqualTo("username", "nouser")).thenReturn(query);
      when(query.limit(1)).thenReturn(query);
      when(query.get()).thenReturn(querySnapshotApiFuture);
      when(querySnapshotApiFuture.get()).thenReturn(querySnapshot);
      doReturn(List.of()).when(querySnapshot).getDocuments();

      Optional<User> result = userRepository.findByUsername("nouser");

      assertFalse(result.isPresent());
    }

    @Test
    void testExistsByUsername() throws Exception {
      when(firestore.collection("users")).thenReturn(collectionReference);
      when(collectionReference.whereEqualTo("username", "testuser")).thenReturn(query);
      when(query.limit(1)).thenReturn(query);
      when(query.get()).thenReturn(querySnapshotApiFuture);
      when(querySnapshotApiFuture.get()).thenReturn(querySnapshot);
      doReturn(List.of(queryDocumentSnapshot)).when(querySnapshot).getDocuments();
      when(queryDocumentSnapshot.toObject(User.class)).thenReturn(user);

      assertTrue(userRepository.existsByUsername("testuser"));
    }

    @Test
    void testSave_newUser() {
      when(firestore.collection("users")).thenReturn(collectionReference);
      when(collectionReference.document()).thenReturn(documentReference);
      when(documentReference.getId()).thenReturn("1");
      when(collectionReference.document("1")).thenReturn(documentReference);

      User newUser = User.builder().username("new").build();
      userRepository.save(newUser);

      assertEquals("1", newUser.getId());
      verify(documentReference).set(newUser);
    }

    @Test
    void testFindAll() throws Exception {
      when(firestore.collection("users")).thenReturn(collectionReference);
      when(collectionReference.get()).thenReturn(querySnapshotApiFuture);
      when(querySnapshotApiFuture.get()).thenReturn(querySnapshot);
      doReturn(List.of(queryDocumentSnapshot)).when(querySnapshot).getDocuments();
      when(queryDocumentSnapshot.toObject(User.class)).thenReturn(user);

      List<User> result = userRepository.findAll();

      assertEquals(1, result.size());
      assertEquals(user, result.get(0));
    }

    @Test
    void testFindById_found() throws Exception {
      when(firestore.collection("users")).thenReturn(collectionReference);
      when(collectionReference.document("1")).thenReturn(documentReference);
      when(documentReference.get()).thenReturn(documentSnapshotApiFuture);
      when(documentSnapshotApiFuture.get()).thenReturn(documentSnapshot);
      when(documentSnapshot.exists()).thenReturn(true);
      when(documentSnapshot.toObject(User.class)).thenReturn(user);

      Optional<User> result = userRepository.findById("1");

      assertTrue(result.isPresent());
      assertEquals(user, result.get());
    }

    @Test
    void testFindById_notFound() throws Exception {
      when(firestore.collection("users")).thenReturn(collectionReference);
      when(collectionReference.document("2")).thenReturn(documentReference);
      when(documentReference.get()).thenReturn(documentSnapshotApiFuture);
      when(documentSnapshotApiFuture.get()).thenReturn(documentSnapshot);
      when(documentSnapshot.exists()).thenReturn(false);

      Optional<User> result = userRepository.findById("2");

      assertFalse(result.isPresent());
    }

    @Test
    void testDeleteById() {
      when(firestore.collection("users")).thenReturn(collectionReference);
      when(collectionReference.document("1")).thenReturn(documentReference);

      userRepository.deleteById("1");

      verify(documentReference).delete();
    }
  }