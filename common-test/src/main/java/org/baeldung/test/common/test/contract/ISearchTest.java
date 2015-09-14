package org.baeldung.test.common.test.contract;

public interface ISearchTest {

    void whenSearchByNameIsPerformed_thenNoExceptions();

    // id

    void givenResourceWithIdExists_whenResourceIsSearchedById_thenNoExceptions();

    void givenResourceWithIdExists_whenResourceIsSearchedById_thenSearchOperationIsSuccessful();

    void givenResourceWithIdExists_whenResourceIsSearchedById_thenResourceIsFound();

    void givenResourceWithIdDoesNotExist_whenResourceIsSearchedById_thenResourceIsNotFound();

    // name

    void givenResourceWithNameExists_whenResourceIsSearchedByName_thenNoExceptions();

    void givenResourceWithNameExists_whenResourceIsSearchedByName_thenOperationIsSuccessful();

    void givenResourceWithNameExists_whenResourceIsSearchedByName_thenResourceIsFound();

    void givenResourceWithNameExists_whenResourceIsSearchedByNameLowerCase_thenResourceIsFound();

    void givenResourceWithNameDoesNotExist_whenResourceIsSearchedByName_thenResourceIsNotFound();

    void givenResourceWithNameExists_whenSearchByNegatedNameIsPerformed_thenResourcesAreCorrect();

    // id and name

    void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndCorrectName_thenOperationIsSuccessful();

    void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndCorrectName_thenResourceIsFound();

    void givenResourceWithNameAndIdExists_whenResourceIsSearchedByIncorrectIdAndCorrectName_thenResourceIsNotFound();

    void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndIncorrectName_thenResourceIsNotFound();

    void givenResourceWithNameAndIdExists_whenResourceIsSearchedByIncorrectIdAndIncorrectName_thenResourceIsNotFound();

    // negated

    void givenResourceExists_whenResourceIsSearchedByNegatedName_thenOperationIsSuccessful();

    void givenResourceExists_whenResourceIsSearchedByNegatedId_thenOperationIsSuccessful();

    void givenResourceExists_whenResourceIsSearchedByNegatedId_thenResourceIsNotFound();

    void givenResourcesExists_whenResourceIsSearchedByNegatedId_thenTheOtherResourcesAreFound();

    void givenResourceAndOtherResourcesExists_whenResourceIsSearchedByNegatedName_thenResourcesAreFound();

    void givenResourceAndOtherResourcesExists_whenResourceIsSearchedByNegatedId_thenResourcesAreFound();

    // contains

    void givenResourceWithNameExists_whenResourceIsSearchedByContainsExactName_thenNoExceptions();

    void givenResourceWithNameExists_whenSearchByContainsEntireNameIsPerformed_thenResourceIsFound();

    void givenResourceWithNameExists_whenSearchByContainsPartOfNameIsPerformed_thenResourceIsFound();

    // starts/ends with

    void whenSearchByStartsWithIsPerformed_thenNoExceptions();

    void givenResourceExists_whenSearchByStartsWithEntireNameIsPerformed_thenResourceIsFound();

    void givenResourceExists_whenSearchByStartsWithPartOfNameIsPerformed_thenResourceIsFound();

    void whenSearchByEndsWithIsPerformed_thenNoExceptions();

    void givenResourceExists_whenSearchByEndsWithEntireNameIsPerformed_thenResourceIsFound();

    void givenResourceExists_whenSearchByEndsWithPartOfNameIsPerformed_thenResourceIsFound();

}
