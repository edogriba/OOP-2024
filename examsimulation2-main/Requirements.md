 Journals and papers  @media print { /\* adjusted to print the html to a single-page pdf \*/ body { font-size: 10pt; }

Journals and papers
===================

The program simulates the management of journals and papers. All classes are found in the **journals** package. The main class is **Journals**. The **TestApp** class in the **example** package contains examples and presents the main test cases but not all. Exceptions are thrown using the **JException** class; only the specified checks must be carried out and not all possible ones. If a method throws an exception there is no change in the data present in the main class. Suggestion: every time you have implemented a method, run TestApp to check the result.

The JDK documentation is accessible at URL [https://oop.polito.it/api/](https://oop.polito.it/api/).

R1: Journals, impact factors
----------------------------

Method **int addJournal(String name, double impactFactor)** inserts a new journal with name and impact factor. It throws an exception if the journal (represented by its name) already exists. The result is the number of characters of the name.

Method **double getImpactFactor(String name)** gives the impact factor of the journal indicated by the name. It throws an exception if the journal does not exist.

Method **groupJournalsByImpactFactor()** groups journal names by increasing impact factors. Journal names are listed in alphabetical order.

R2: Authors, papers
-------------------

Method **int registerAuthors (String... authorNames)** adds authors. Duplicated authors are ignored. The result is the number of authors entered so far.

Method **String addPaper (String journalName, String paperTitle, String... authorNames)** adds a paper to a journal. The journal is indicated by its name; the method throws an exception if the journal does not exist. The paper has a title that must be unique in the specified journal, otherwise an exception is thrown. The paper can have one or more authors: if not all have been registered, an exception is thrown. The result is the journal name followed by ":" and the paper title.

Method **giveNumberOfPapersByJournal()** gives the number of papers for each journal. Journals are sorted alphabetically. Journals without papers are ignored.

R3: Impact factors
------------------

Method **double getAuthorImpactFactor(String authorName)** gives the impact factor for the author indicated. The impact factor of an author is obtained by adding the impact factors of his/her papers. The impact factor of a paper is equal to that of the journal containing the paper. The method throws an exception if the author has not been registered. If the author has no papers the result is 0.0.

Method **getImpactFactorsByAuthors()** groups authors (in alphabetical order) by increasing impact factors. Authors without papers are ignored.

R4: Statistics
--------------

Method **getNumberOfPapersByAuthor()** gives the number of papers by author; authors are sorted alphabetically. Authors without papers are ignored.

Method **String getJournalWithTheLargestNumberOfPapers()** gives the name of the journal having the largest number of papers. If the largest number of papers is common to two or more journals the result is the name of the journal which is the first in alphabetical order.