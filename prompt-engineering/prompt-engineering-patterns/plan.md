# Plan: Spring AI Prompt Engineering Learning Project

## Overview
5-phase progressive learning plan using the existing `prompt-engineering-patterns` project as the foundation.
- Stack: Spring Boot 4.0.1, Spring AI 2.0.0-SNAPSHOT, Anthropic Claude, Java 17
- ANTHROPIC_API_KEY already available in env
- Entry point: `PromptEngineeringApplication.java` (CommandLineRunner, ~17 patterns)

---

## Phase 1: Run existing project (STEP 1 - READY TO EXECUTE)
Goal: Build and run the project to observe all 17 patterns working end-to-end.

Steps:
1a. Verify ANTHROPIC_API_KEY is set in env
1b. Build: `./mvnw clean package -q` from project root
1c. Run all patterns: `./mvnw spring-boot:run`
1d. Or run specific group: `./mvnw spring-boot:run -Dspring-boot.run.arguments=basic`
   - Args: `basic`, `system`, `advanced`, `all` (default)

Files:
- `src/main/java/.../PromptEngineeringApplication.java` — main runner
- `src/main/resources/application.properties` — API key config (reads ANTHROPIC_API_KEY)
- `pom.xml` — Spring Boot 4.0.1, Spring AI 2.0.0-SNAPSHOT, needs snapshot repo

Note: Uses Spring AI snapshot repo (https://repo.spring.io/snapshot). First build may be slow.

## Phase 2: Add new prompt engineering patterns
Goal: Extend with patterns not yet covered.
Candidates: ReAct (Reason+Act), Generated Knowledge, Directional Stimulus, Meta-prompting
Pattern: add public method `pt_<name>(ChatClient chatClient)`, register in `runAllPatterns()` and `runAdvancedPatterns()`.

## Phase 3: Add REST API layer
Goal: Expose each pattern as an HTTP endpoint for interactive use.
- Add `@RestController` classes per pattern category
- Switch `spring.main.web-application-type=none` -> `servlet`
- Each endpoint accepts user input, returns pattern output

## Phase 4: Add better code comments / documentation
Goal: Annotate each pattern method with inline explanations for learning.
- Cross-reference Google Prompt Engineering Guide page numbers (already partially done)
- Add Javadoc to each `pt_*` method explaining when/why to use the technique
- Annotate `application.properties` with richer config explanations

## Phase 5: Create a simpler starter project
Goal: Minimal "hello prompt" project for absolute beginners.
- Single `ChatClient` call demonstrating zero-shot, few-shot, and system prompt
- Stripped-down pom.xml
- README walkthrough

---

## Decisions
- Phases are sequential (each builds on prior)
- Phase 1 is non-destructive: only runs, no code changes needed
- API key: read from ANTHROPIC_API_KEY env var (already wired in application.properties)

## Status
- User approved plan on 2026-04-24
- Phase 1 completed on 2026-04-24
- Build fix applied: updated `ChatClient.options(...)` and `defaultOptions(...)` calls to pass `ChatOptions.Builder` (Spring AI 2.0 API)
- Runtime validated: `./mvnw spring-boot:run` finished with BUILD SUCCESS and "Prompt engineering patterns demonstration completed!"
